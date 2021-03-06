
--DROP USER NVCL CASCADE;
--DROP TABLESPACE "NVCLINDTS" INCLUDING CONTENTS AND DATAFILES;
--DROP TABLESPACE "NVCLTBLSPC" INCLUDING CONTENTS AND DATAFILES;
--DROP TABLESPACE "NVCLLOBTS" INCLUDING CONTENTS AND DATAFILES;
--DROP ROLE NVCLAnalyst;
--DROP ROLE NVCLViewer;
--DROP ROLE WEBSERVICE;

-- above commands will remove any existing NVCL database. WARNING: this will delete ALL data in the existing NVCL schema

-- ensure you enter filenames and paths for the tablespaces before running this script
-- eg. f:\oracle\data\NVCLINDTS

CREATE SMALLFILE TABLESPACE "NVCLINDTS" DATAFILE '<index data file filename including path>' SIZE 1024M REUSE AUTOEXTEND ON NEXT 1024M EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;
CREATE SMALLFILE TABLESPACE "NVCLTBLSPC" DATAFILE '<table data file filename including path>' SIZE 2048M REUSE AUTOEXTEND ON NEXT 2048M EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO BLOCKSIZE 8k;
CREATE SMALLFILE TABLESPACE "NVCLLOBTS" DATAFILE '<LOB file filename including path>' SIZE 8192M REUSE AUTOEXTEND ON NEXT 8192M EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO BLOCKSIZE 8k;

CREATE USER NVCL identified by passw0rd DEFAULT TABLESPACE NVCLTBLSPC;
grant resource to NVCL;
grant create view to NVCL;
GRANT CREATE MATERIALIZED VIEW to NVCL;
GRANT UNLIMITED TABLESPACE TO NVCL;

--grant connect to NVCL;  --WARNING: only do this if you intend to run the createnvcldb scripts as the NVCL user and remember to either revoke this privilege afterwards and set a secure password for nvcl

CREATE ROLE NVCLAnalyst;
CREATE ROLE NVCLViewer;
CREATE ROLE WEBSERVICE;
