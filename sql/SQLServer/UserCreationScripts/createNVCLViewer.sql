-- NVCL create Viewer user script
-- Use this script to create a user account for all users who require TSG viewer access to the nvcl DB.  This is read only account.
-- Usually you will only need 1 account of this type used by all TSG Viewers
-- replace the 3 occurences of <username> with the desired username (eg Viewer) and <password> with their password 
CREATE LOGIN [<username>] WITH PASSWORD='<password>', DEFAULT_DATABASE=[NVCL]
GO
USE [NVCL]
GO
CREATE USER [<username>] FOR LOGIN [<username>] WITH DEFAULT_SCHEMA=[dbo];
GO
EXEC sp_addrolemember N'TSGVIEWER', N'<username>'
GO
