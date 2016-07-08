-- NVCL create Viewer user script
-- Use this script to create a user account for all users who require TSG viewer access to the nvcl DB.  This is read only account.
-- Usually you will only need 1 account of this type used by all TSG Viewers
-- replace the 3 occurences of <username> with the desired username (eg Viewer) and <password> with their password 

create user <username> identified by <password>;

grant connect to <username>;

call NVCL.SETUPNVCLVIEWER('<username>');
