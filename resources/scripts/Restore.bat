@echo off
REM
REM The script assumes that user can connect using "/ as sysdba"
REM
REM =================
REM Restore procedure
REM =================
REM
REM    If Installed Oracle home is also lost and oracle binaries were 
REM    re-installed or the Oracle is installed to new oracle home location 
REM    compared to backup time, then user will be prompted to enter Flash 
REM    Recovery Area location.
REM
REM    For database in NoArchiveLog mode, database is restored to last offline 
REM    backup time/scn;
REM    For database in Archive log mode, database is restored from last backup 
REM    and a complete recovery is attempted. If complete recovery fails, 
REM    user can open the database with resetlogs option provided the files 
REM    are not recovery fuzzy.
REM
REM    The restore log is saved in ?/DATABASE/OXE_RESTORE.LOG
REM

setlocal

set /p inp="This operation will shut down and restore the database. Are you sure [Y/N]?"
:checkinp
if /i "%inp%" == "Y" goto :confirmedyes
if /i "%inp%" == "n" exit
:Askagain
set /p inp=
goto :checkinp

:confirmedyes

echo Restore in progress...

echo db_name=xe >%temp%\rman_dummy.ora
echo sga_target=270M >>%temp%\rman_dummy.ora


net start oracleserviceXe

REM Startup database in nomount mode using RMAN...
@(
echo set echo on^;
echo startup nomount pfile=%temp%\rman_dummy.ora force^;
) > %temp%\restore_rman0.dat
rman target / @%temp%\restore_rman0.dat
if not %errorlevel% == 0 set Errorstr=         RMAN Error - could not startup dummy instance & goto :restorefailederr

@(
echo connect / as sysdba^;
echo set head off
echo set echo off
echo set linesize 515
echo variable var varchar2^(512^)^;
echo execute :var := sys.dbms_backup_restore.normalizefilename^(^'SPFILE2INIT^'^)^;
echo spool %temp%\spfile2init.log
echo select sys.dbms_backup_restore.normalizefilename^(^'SPFILE2INIT.ORA^'^) spfile2init from dual^;
echo exit^;
) > %temp%\spfile2init.sql
sqlplus /nolog @%temp%\spfile2init.sql >nul
FOR /F %%i in (%temp%\spfile2init.log) do set SPFILE2INIT=%%i

@(
echo connect / as sysdba;
   echo set head off
   echo set echo off
   echo set linesize 515
   echo variable var varchar2^(512^)^;
   echo execute :var := sys.dbms_backup_restore.normalizefilename^(^'FRA_LOC^'^)^;
   echo spool %temp%\restore_rmanlog.log
   echo select sys.dbms_backup_restore.normalizefilename^(^'OXE_RESTORE.LOG^'^) RESTORE_RMANLOG from dual^;
   echo exit^;
) > %temp%\restore_rmanlog.sql
sqlplus /nolog @%temp%\restore_rmanlog.sql >nul
FOR /F %%i in (%temp%\restore_rmanlog.log) do set RESTORE_RMANLOG=%%i

if not exist ^"%SPFILE2INIT%^" goto get_rcvarea_loc
@(
   echo set echo on^;
   echo shutdown immediate^;
   echo startup nomount pfile=^"%SPFILE2INIT%^"^;
   echo restore ^(spfile from autobackup^) ^(controlfile from autobackup^)^;
   echo startup mount force^;
   echo configure controlfile autobackup off^;
   echo restore database^;
) > %temp%\restore_rman1.dat
rman target / @%temp%\restore_rman1.dat trace "%RESTORE_RMANLOG%"
if not %errorlevel% == 0 set Errorstr=         RMAN Error - See log for error & goto :restorefailederr
goto restored_files

:get_rcvarea_loc
set /p rcvarea_loc="Enter the flash recovery area location:"
@(
   echo set echo on^;
   echo restore ^(spfile from autobackup db_recovery_file_dest=^'%rcvarea_loc%^'^)^;
   echo startup nomount force^;
   echo restore ^(controlfile from autobackup^)^;
   echo alter database mount^;
   echo configure controlfile autobackup off^;
   echo restore database^;
) > %temp%\restore_rman1.dat
rman target / @%temp%\restore_rman1.dat trace "%RESTORE_RMANLOG%"
if not %errorlevel% == 0 set Errorstr=         RMAN Error - See log for error & goto :restorefailederr
goto restored_files

:restored_files
@(
   echo connect / as sysdba^;
   echo declare cursor n1 is select name from v$tempfile^;
   echo begin 
   echo for a in n1
   echo   loop
   echo     begin
   echo       sys.dbms_backup_restore.deletefile^(a.name^)^;
   echo     exception
   echo       when others then
   echo         null^;
   echo     end^;
   echo end loop^;
   echo end^;
   echo /
   echo exit^;
   echo /
) > %temp%\deltfile.sql
sqlplus /nolog @%temp%\deltfile.sql >nul
@(
   echo connect / as sysdba^;
   echo set head off
   echo set echo off
   echo spool %temp%\logmode.log
   echo select log_mode from v$database^;
   echo exit^;
) > %temp%\logmode.sql
sqlplus /nolog @%temp%\logmode.sql >nul
FOR /F %%i in (%temp%\logmode.log) do set LOGMODE=%%i

if "%LOGMODE%" == "NOARCHIVELOG" goto process_noarchivelog
if "%LOGMODE%" == "ARCHIVELOG" goto process_archivelog
set Errorstr=         Unknown log mode : %LOGMODE%
goto :restorefailederr

:process_noarchivelog
@(
   echo set echo on^;
   echo alter database open resetlogs;
) > %temp%\restore_rman2.dat
rman target / @%temp%\restore_rman2.dat trace "%RESTORE_RMANLOG%" append
if not %errorlevel% == 0 set Errorstr=         RMAN Error - See log for details & goto :restorefailederr
goto :restoresucess

:process_archivelog
@(
   echo set echo on^;
   echo recover database^;
   echo alter database open resetlogs;
) > %temp%\restore_rman2.dat
rman target / @%temp%\restore_rman2.dat trace "%RESTORE_RMANLOG%" append
if not %errorlevel% == 0 set Errorstr=         RMAN Error - See log for details & goto :restorefailederr
goto :restoresucess

:restoresucess
echo Restore of the database succeeded.
echo Log file is at %RESTORE_RMANLOG%.
pause Press any key to exit
exit
goto :EOF

:restorefailederr
echo ====================   ERROR =============================
echo          Restore of the database failed.
echo %Errorstr%.
echo          Log file is at %RESTORE_RMANLOG%.
echo ====================   ERROR =============================
pause Press any key to exit
exit
goto :EOF
