USE [NVCL]
GO
CREATE ROLE [NVCLANALYST] AUTHORIZATION [dbo]
GO
CREATE ROLE [TSGVIEWER] AUTHORIZATION [dbo]
GO
CREATE ROLE [WEBSERVICE] AUTHORIZATION [dbo]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DECIMALLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[SCALARGROUP_ID] [int] NULL,
	[MINTHRESHOLD] [float] NULL,
	[MAXTHRESHOLD] [float] NULL,
	[RESULTISRGBCOLOUR] [numeric](1, 0) NULL,
 CONSTRAINT [DECIMALLOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEDECIMALLOG]  
   @v_logid varchar(max),
   @v_scalgroupid int,
   @v_minthreshold float(53),
   @v_maxthreshold float(53),
   @v_isrgbcolour int
AS 

   BEGIN
      UPDATE dbo.DECIMALLOGS
         SET 
            SCALARGROUP_ID = @v_scalgroupid, 
            MINTHRESHOLD = @v_minthreshold, 
            MAXTHRESHOLD = @v_maxthreshold, 
            RESULTISRGBCOLOUR = @v_isrgbcolour
      WHERE DECIMALLOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEDECIMALLOG]  
   @v_logid varchar(max),
   @v_scalgroupid int,
   @v_minthreshold float(53),
   @v_maxthreshold float(53),
   @v_isrgbcolour int
AS 
   
   BEGIN
      INSERT dbo.DECIMALLOGS(
         LOG_ID, 
         SCALARGROUP_ID, 
         MINTHRESHOLD, 
         MAXTHRESHOLD, 
         RESULTISRGBCOLOUR)
         VALUES (
            @v_logid, 
            @v_scalgroupid, 
            @v_minthreshold, 
            @v_maxthreshold, 
            @v_isrgbcolour)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DECIMALLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[DECIMALVALUE] [float] NULL,
 CONSTRAINT [DECIMALLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEDECIMALLOGDATA]  
   @v_declog_id varchar(max),
   @v_samplenumber int,
   @v_decvalue float(53)
AS 

   BEGIN
      UPDATE dbo.DECIMALLOGDATA
         SET 
            DECIMALVALUE = @v_decvalue
      WHERE DECIMALLOGDATA.LOG_ID = @v_declog_id AND DECIMALLOGDATA.SAMPLENUMBER = @v_samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEDECIMALLOGDATA]  
   @v_declog_id varchar(max),
   @v_samplenumber int,
   @v_decvalue float(53)
AS 

   BEGIN
      INSERT dbo.DECIMALLOGDATA(LOG_ID, SAMPLENUMBER, DECIMALVALUE)
         VALUES (@v_declog_id, @v_samplenumber, @v_decvalue)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MASKLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[SCALARGROUP_ID] [int] NULL,
 CONSTRAINT [MASKLOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEMASKLOG]  
   @v_logid varchar(max),
   @v_scalgroupid int
AS 

   BEGIN
      UPDATE dbo.MASKLOGS
         SET 
            SCALARGROUP_ID = @v_scalgroupid
      WHERE MASKLOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEMASKLOG]  
   @v_logid varchar(max),
   @v_scalgroupid int
AS 

   BEGIN
      INSERT dbo.MASKLOGS(LOG_ID, SCALARGROUP_ID)
         VALUES (@v_logid, @v_scalgroupid)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MASKLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[MASKVALUE] [numeric](1, 0) NULL,
 CONSTRAINT [MASKLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEMASKLOGDATA]  
   @v_masklog_id varchar(max),
   @v_samplenumber int,
   @v_maskvalue int
AS 

   BEGIN
      UPDATE dbo.MASKLOGDATA
         SET 
            MASKVALUE = @v_maskvalue
      WHERE MASKLOGDATA.LOG_ID = @v_masklog_id AND MASKLOGDATA.SAMPLENUMBER = @v_samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEMASKLOGDATA]  
   @v_masklog_id varchar(max),
   @v_samplenumber int,
   @v_maskvalue int
AS 
   
   BEGIN
      INSERT dbo.MASKLOGDATA(LOG_ID, SAMPLENUMBER, MASKVALUE)
         VALUES (@v_masklog_id, @v_samplenumber, @v_maskvalue)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROFLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[LOGTIMESTAMP] [datetime] NULL,
	[FLOATSPERSAMPLE] [int] NULL,
	[MINVAL] [float] NULL,
	[MAXVAL] [float] NULL,
 CONSTRAINT [PROFLOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEPROFLOG]  
   @v_logid varchar(max),
   @v_logtime datetime,
   @v_minval float(53),
   @v_maxval float(53),
   @v_floatspersample int
AS 

   BEGIN
      UPDATE dbo.PROFLOGS
         SET 
            LOGTIMESTAMP = @v_logtime, 
            MINVAL = @v_minval, 
            MAXVAL = @v_maxval,
			FLOATSPERSAMPLE=@v_floatspersample
      WHERE PROFLOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEPROFLOG]  
   @v_logid varchar(max),
   @v_logtime datetime,
   @v_minval float(53),
   @v_maxval float(53),
   @v_floatspersample int
AS 
   
   BEGIN
      INSERT dbo.PROFLOGS(LOG_ID, LOGTIMESTAMP, MINVAL, MAXVAL, FLOATSPERSAMPLE)
         VALUES (@v_logid, @v_logtime, @v_minval, @v_maxval, @v_floatspersample)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PROFLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[PROFILOMETERVALUES] [varbinary](max) NULL,
 CONSTRAINT [PROFLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEPROFLOGDATA]  
   @v_proflog_id varchar(max),
   @v_samplenumber int,
   @v_profdata varbinary(max)
AS 

   BEGIN
      UPDATE dbo.PROFLOGDATA
         SET 
            PROFILOMETERVALUES = @v_profdata
      WHERE PROFLOGDATA.LOG_ID = @v_proflog_id AND PROFLOGDATA.SAMPLENUMBER = @v_samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEPROFLOGDATA]  
   @v_proflog_id varchar(max),
   @v_samplenumber int,
   @v_profdata varbinary(max)
AS 

   BEGIN
      INSERT dbo.PROFLOGDATA(LOG_ID, SAMPLENUMBER, PROFILOMETERVALUES)
         VALUES (@v_proflog_id, @v_samplenumber, @v_profdata)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SPECTRALLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[SPECTRALSAMPLINGPOINTS] [varbinary](max) NULL,
	[FWHM] [varbinary](max) NULL,
	[SPECTRALUNITS] [varchar](4000) NULL,
	[LOGTIMESTAMP] [datetime] NULL,
	[LAYERORDER] [int] NULL
) ON [PRIMARY]
SET ANSI_PADDING OFF
ALTER TABLE [dbo].[SPECTRALLOGS] ADD [TIRQ] [varbinary](max) NULL
ALTER TABLE [dbo].[SPECTRALLOGS] ADD  CONSTRAINT [SPECTRALLOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATESPECTRALLOG]  
   @v_logid varchar(max),
   @v_specsamppoints varbinary(max),
   @v_fwhm varbinary(max),
   @v_specunits varchar(max),
   @v_logtime datetime,
   @v_logorder int,
   @v_tirq varbinary(max)
AS 

   BEGIN
      UPDATE dbo.SPECTRALLOGS
         SET 
            SPECTRALSAMPLINGPOINTS = @v_specsamppoints, 
            FWHM = @v_fwhm, 
            SPECTRALUNITS = @v_specunits, 
            LOGTIMESTAMP = @v_logtime, 
            LAYERORDER = @v_logorder,
			TIRQ=@v_tirq
      WHERE SPECTRALLOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATESPECTRALLOG]  
   @v_logid varchar(max),
   @v_specsamppoints varbinary(max),
   @v_fwhm varbinary(max),
   @v_specunits varchar(max),
   @v_logtime datetime,
   @v_logorder int,
   @v_tirq varbinary(max)
AS 
   
   BEGIN
      INSERT dbo.SPECTRALLOGS(
         LOG_ID, 
         SPECTRALSAMPLINGPOINTS, 
         FWHM, 
         SPECTRALUNITS, 
         LOGTIMESTAMP, 
         LAYERORDER,
		 TIRQ)
         VALUES (
            @v_logid, 
            @v_specsamppoints, 
            @v_fwhm, 
            @v_specunits, 
            @v_logtime, 
            @v_logorder,
			@v_tirq)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SPECTRALLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[SPECTRALVALUES] [varbinary](max) NULL,
 CONSTRAINT [SPECTRALLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATESPECTRALLOGDATA]  
   @v_speclog_id varchar(max),
   @v_samplenumber int,
   @v_spectrum varbinary(max)
AS 
   
   BEGIN
      UPDATE dbo.SPECTRALLOGDATA
         SET 
            SPECTRALVALUES = @v_spectrum
      WHERE SPECTRALLOGDATA.LOG_ID = @v_speclog_id AND SPECTRALLOGDATA.SAMPLENUMBER = @v_samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATESPECTRALLOGDATA]  
   @v_speclog_id varchar(max),
   @v_samplenumber int,
   @v_spectrum varbinary(max)
AS 
   
   BEGIN
      INSERT dbo.SPECTRALLOGDATA(LOG_ID, SAMPLENUMBER, SPECTRALVALUES)
         VALUES (@v_speclog_id, @v_samplenumber, @v_spectrum)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SCALARSETS](
	[DATASET_ID] [varchar](64) NOT NULL,
	[SETNUMBER] [numeric](2, 0) NOT NULL,
	[SETNAME] [varchar](20) NULL,
	[SETTYPE] [numeric](2, 0) NULL,
 CONSTRAINT [SCALARSET_PK] PRIMARY KEY CLUSTERED 
(
	[DATASET_ID] ASC,
	[SETNUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATESCALARSET]  
   @v_dsid varchar(max),
   @v_setnumber int,
   @v_setname varchar(max),
   @v_settype int
AS 
   
   BEGIN

      DECLARE
         @setexists int

      SELECT @setexists = count(*)
      FROM dbo.SCALARSETS
      WHERE SCALARSETS.DATASET_ID = @v_dsid AND SCALARSETS.SETNUMBER = @v_setnumber

      IF (@setexists = 1)
         UPDATE dbo.SCALARSETS
            SET 
               SETNAME = @v_setname, 
               SETTYPE = @v_settype
         WHERE SCALARSETS.DATASET_ID = @v_dsid AND SCALARSETS.SETNUMBER = @v_setnumber
      ELSE 
         INSERT dbo.SCALARSETS(DATASET_ID, SETNUMBER, SETNAME, SETTYPE)
            VALUES (@v_dsid, @v_setnumber, @v_setname, @v_settype)

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[IMAGELOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[IMGHISTOGRAM] [varbinary](max) NULL,
	[IMGCLIPPERCENT] [bigint] NULL,
	[LOGTIMESTAMP] [datetime] NULL,
	[IMGWIDTH] [int] NULL,
	[IMGHEIGHT] [int] NULL,
 CONSTRAINT [IMAGELOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEIMAGELOG]  
   @v_logid varchar(max),
   @v_imghist varbinary(max),
   @v_imgclippercent int,
   @v_logtime datetime,
   @v_imgwidth int,
   @v_imgheight int
AS 
   
   BEGIN
      UPDATE dbo.IMAGELOGS
         SET 
            IMGHISTOGRAM = @v_imghist, 
            IMGCLIPPERCENT = @v_imgclippercent, 
            LOGTIMESTAMP = @v_logtime, 
            IMGWIDTH = @v_imgwidth, 
            IMGHEIGHT = @v_imgheight
      WHERE IMAGELOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEIMAGELOG]  
   @v_logid varchar(max),
   @v_imghist varbinary(max),
   @v_imgclippercent int,
   @v_logtime datetime,
   @v_imgwidth int,
   @v_imgheight int
AS 

   BEGIN
      INSERT dbo.IMAGELOGS(
         LOG_ID, 
         IMGHISTOGRAM, 
         IMGCLIPPERCENT, 
         LOGTIMESTAMP, 
         IMGWIDTH, 
         IMGHEIGHT)
         VALUES (
            @v_logid, 
            @v_imghist, 
            @v_imgclippercent, 
            @v_logtime, 
            @v_imgwidth, 
            @v_imgheight)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[IMAGELOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[IMAGEDATA] [varbinary](max) NULL,
	[IMAGECOMMENT] [varchar](4000) NULL,
 CONSTRAINT [IMAGELOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEIMAGELOGDATA]  
   @v_imagelog_id varchar(max),
   @v_samplenumber int,
   @v_imagedata varbinary(max),
   @v_imgcomment varchar(max)
AS 
   
   BEGIN
      UPDATE dbo.IMAGELOGDATA
         SET 
            SAMPLENUMBER = @v_samplenumber, 
            IMAGEDATA = @v_imagedata, 
            IMAGECOMMENT = @v_imgcomment
      WHERE IMAGELOGDATA.LOG_ID = @v_imagelog_id
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ALGORITHMOUTPUTS](
	[ALGORITHMOUTPUT_ID] [int] NOT NULL,
	[ALGORITHMOUTPUTNAME] [varchar](4000) NOT NULL,
	[SCRIPT] [varchar](4000) NULL,
	[DESCRIPTION] [varchar](4000) NULL,
	[OUTPUTLOGTYPE] [int] NULL,
	[ALGORITHM_ID] [int] NULL,
	[ALGVERSION] [numeric](8, 4) NULL,
 CONSTRAINT [ALGORITHMOUTPUTS_PK] PRIMARY KEY CLUSTERED 
(
	[ALGORITHMOUTPUT_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[LOGNAME] [varchar](4000) NULL,
	[CREATORUSERNAME] [varchar](4000) NULL,
	[MODIFIEDDATE] [datetime] NULL,
	[DATASET_ID] [varchar](64) NOT NULL,
	[DOMAINLOG_ID] [varchar](64) NULL,
	[LOGTYPE] [int] NULL,
	[CREATEDDATE] [datetime] NULL,
	[DESCRIPTION] [varchar](4000) NULL,
	[ISPUBLIC] [numeric](1, 0) NOT NULL,
	[MODIFIERUSERNAME] [varchar](4000) NULL,
	[ALGORITHMOUTPUT_ID] [int] NULL,
	[MASKLOG_ID] [varchar](64) NULL,
	[REFSTATS_ID] [varchar](64) NULL,
	[TSARETRAINING_ID] [varchar](64) NULL,
	[TSGHANDMASK] [bigint] NULL,
	[CUSTOMSCRIPT] [varchar](max) NULL,
	[BATCHSCRIPT] [varchar](max) NULL,
	[MIXNUMBER] [numeric](2, 0) NULL,
	[AUXSPECTRALLAYER_ID] [varchar](64) NULL,
	[LOCALSPECTRALLAYER_ID] [varchar](64) NULL,
	[PLS_ID] [varchar](64) NULL,
 CONSTRAINT [LOGS_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING OFF
GO
CREATE TABLE [dbo].[CALIBRATIONLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[CALIBRATIONDATA] [varbinary](max) NULL,
 CONSTRAINT [CALIBRATIONLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CLASSLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[CLASSLOGVALUE] [int] NULL,
 CONSTRAINT [CLASSLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DOMAINLOGDATA](
	[LOG_ID] [varchar](64) NOT NULL,
	[SAMPLENUMBER] [int] NOT NULL,
	[STARTVALUE] [numeric](18, 5) NULL,
	[ENDVALUE] [numeric](18, 5) NULL,
	[SAMPLENAME] [varchar](4000) NULL,
	[DESCRIPTION] [varchar](4000) NULL,
	[COLOUR] [int] NULL,
	[SWIRMINLIST] [varbinary](max) NULL,
	[SWIRTSAVERSION] [int] NULL,
	[VNIRMINLIST] [varbinary](max) NULL,
	[VNIRTSAVERSION] [int] NULL,
	[TIRMINLIST] [varbinary](max) NULL,
	[TIRTSAVERSION] [int] NULL,
	[AMINLIST] [varbinary](max) NULL,
	[ATSAVERSION] [int] NULL DEFAULT ((0)),
	[BMINLIST] [varbinary](max) NULL,
	[BTSAVERSION] [int] NULL DEFAULT ((0)),
 CONSTRAINT [DOMAINLOGDATA_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[SAMPLENUMBER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[GETDATAPOINTS] 
( 
   @v_logid varchar(max)
)

RETURNS int
AS
BEGIN

      DECLARE
         @curlogtype int, 
         @datapointcount int

      SET @datapointcount=0

         SELECT @curlogtype = LOGS.LOGTYPE
         FROM dbo.LOGS
         WHERE LOGS.LOG_ID = @v_logid

         IF @curlogtype = 0
            BEGIN

               SELECT @datapointcount = count_big(*)
               FROM dbo.DOMAINLOGDATA
               WHERE DOMAINLOGDATA.LOG_ID = @v_logid

            END
         ELSE 
            IF @curlogtype = 1
               BEGIN

                  SELECT @datapointcount = count_big(*)
                  FROM dbo.CLASSLOGDATA
                  WHERE CLASSLOGDATA.LOG_ID = @v_logid

               END
            ELSE 
               IF @curlogtype = 2
                  BEGIN

                     SELECT @datapointcount = count_big(*)
                     FROM dbo.DECIMALLOGDATA
                     WHERE DECIMALLOGDATA.LOG_ID = @v_logid

                  END
               ELSE 
                  IF @curlogtype = 3
                     BEGIN

                        SELECT @datapointcount = count_big(*)
                        FROM dbo.IMAGELOGDATA
                        WHERE IMAGELOGDATA.LOG_ID = @v_logid

                     END
                  ELSE 
                     IF @curlogtype = 4
                        BEGIN

                           SELECT @datapointcount = count_big(*)
                           FROM dbo.PROFLOGDATA
                           WHERE PROFLOGDATA.LOG_ID = @v_logid

                        END
                     ELSE 
                        IF @curlogtype = 5
                           BEGIN

                              SELECT @datapointcount = count_big(*)
                              FROM dbo.SPECTRALLOGDATA
                              WHERE SPECTRALLOGDATA.LOG_ID = @v_logid

                           END
                        ELSE 
                           IF @curlogtype = 6
                              BEGIN

                                 SELECT @datapointcount = count_big(*)
                                 FROM dbo.MASKLOGDATA
                                 WHERE MASKLOGDATA.LOG_ID = @v_logid

                              END
                           ELSE 
                             IF @curlogtype = 7
                               BEGIN

                                 SELECT @datapointcount = count_big(*)
                                 FROM dbo.CALIBRATIONLOGDATA
                                 WHERE CALIBRATIONLOGDATA.LOG_ID = @v_logid

                              END

      RETURN  @datapointcount

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MACHINES](
	[MACHINE_ID] [int] NOT NULL,
	[MACHINENAME] [varchar](4000) NOT NULL,
	[NAMESPACEID] [varchar](4000) NULL,
 CONSTRAINT [MACHINES_PK] PRIMARY KEY CLUSTERED 
(
	[MACHINE_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DATASETS](
	[DATASET_ID] [varchar](64) NOT NULL,
	[DATASETNAME] [varchar](4000) NULL,
	[DOMAIN_ID] [varchar](64) NULL,
	[ISREFERENCELIBRARY] [numeric](1, 0) NULL,
	[CREATEDDATE] [datetime] NULL,
	[MODIFIEDDATE] [datetime] NULL,
	[CREATORUSERNAME] [varchar](4000) NULL,
	[MODIFIERUSERNAME] [varchar](4000) NULL,
	[PRIMARYLOGGER_ID] [int] NULL,
	[SPECLOG_ID] [varchar](64) NULL,
	[IMAGELOG_ID] [varchar](64) NULL,
	[PROFLOG_ID] [varchar](64) NULL,
	[TRAYLOG_ID] [varchar](64) NULL,
	[SECTIONLOG_ID] [varchar](64) NULL,
	[TSGLAYOUT] [varchar](max) NULL,
	[HOLEDATASOURCENAME] [varchar](4000) NULL,
	[HOLEIDENTIFIER] [varchar](256) NULL,
	[DSDESCRIPTION] [varchar](4000) NULL,
	[ORIGAUTHOR] [varchar](4000) NULL,
	[IMPORTDATE] [datetime] NULL,
	[SCANDATE] [datetime] NULL,
	[CUSTOMDATASET_ID] [varchar](64) NULL,
	[CUSTCALCDATASET_ID] [varchar](64) NULL,
	[ISPUBLIC] [numeric](1, 0) NOT NULL,
	[CHK_L0ARCHIVED] [int] NOT NULL DEFAULT ((0)),
	[CHK_FMSTATUS] [int] NOT NULL DEFAULT ((0)),
	[CHK_TIDLSTATUS] [int] NOT NULL DEFAULT ((0)),
	[CHK_IMGSTATUS] [int] NOT NULL DEFAULT ((0)),
	[CHK_SUTSA] [int] NOT NULL DEFAULT ((0)),
	[CHK_VUTSA] [int] NOT NULL DEFAULT ((0)),
	[CHK_TUTSA] [int] NOT NULL DEFAULT ((0)),
	[CHK_IMPORTS] [int] NOT NULL DEFAULT ((0)),
	[CHK_RMARKS] [int] NOT NULL DEFAULT ((0)),
	[CHK_BATSTATUS] [int] NOT NULL DEFAULT ((0)),
	[CHK_SCLRSTATUS] [int] NOT NULL DEFAULT ((0)),
	[CHK_DOMAINS] [int] NOT NULL DEFAULT ((0)),
	[CHK_PLOTS] [int] NOT NULL DEFAULT ((0)),
	[CHK_LAYOUTS] [int] NOT NULL DEFAULT ((0)),
	[CHK_DBASE] [int] NOT NULL DEFAULT ((0)),
 CONSTRAINT [TSGDATASET_PK] PRIMARY KEY CLUSTERED 
(
	[DATASET_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'COMMENT', @value=N'This table contains dataset specific information.  This data is used to recreate an analysis session within "The Spectral Geologist" software application.  Schema_Version=1.1' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DATASETS'
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[PUBLISHEDDATASETS] (
   DATASET_ID,
   GML_ID,
   GML_NAME,   
   DATASETNAME, 
   DOMAIN_ID, 
   ISREFERENCELIBRARY, 
   CREATEDDATE, 
   MODIFIEDDATE, 
   CREATORUSERNAME, 
   MODIFIERUSERNAME, 
   SPECLOG_ID, 
   IMAGELOG_ID, 
   PROFLOG_ID, 
   TRAYLOG_ID, 
   SECTIONLOG_ID,  
   HOLEDATASOURCENAME, 
   HOLEIDENTIFIER, 
   DSDESCRIPTION, 
   ORIGAUTHOR, 
   IMPORTDATE, 
   SCANDATE, 
   CUSTOMDATASET_ID, 
   CUSTCALCDATASET_ID, 
   MACHINENAME)
AS
SELECT 
      DATASETS.DATASET_ID,
	  'sa.samplingfeaturecollection.' + datasets.DATASET_ID, 
      'urn:ogc:feature:SamplingFeatureCollection:SamplingFeatureCollection.' + datasets.DATASET_ID, 
      DATASETS.DATASETNAME, 
      DATASETS.DOMAIN_ID, 
      DATASETS.ISREFERENCELIBRARY, 
      CAST(DATASETS.CREATEDDATE AS datetime), 
      CAST(DATASETS.MODIFIEDDATE AS datetime), 
      DATASETS.CREATORUSERNAME, 
      DATASETS.MODIFIERUSERNAME, 
      DATASETS.SPECLOG_ID, 
      DATASETS.IMAGELOG_ID, 
      DATASETS.PROFLOG_ID, 
      DATASETS.TRAYLOG_ID, 
      DATASETS.SECTIONLOG_ID, 
      DATASETS.HOLEDATASOURCENAME, 
      DATASETS.HOLEIDENTIFIER, 
      DATASETS.DSDESCRIPTION, 
      DATASETS.ORIGAUTHOR, 
      DATASETS.IMPORTDATE, 
      DATASETS.SCANDATE, 
      DATASETS.CUSTOMDATASET_ID, 
      DATASETS.CUSTCALCDATASET_ID, 
      MACHINES.MACHINENAME
   FROM 
      dbo.DATASETS 
         LEFT OUTER JOIN dbo.MACHINES 
         ON DATASETS.PRIMARYLOGGER_ID = MACHINES.MACHINE_ID
   WHERE DATASETS.ISPUBLIC = 1
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[PUBLISHEDLOGS] (
   LOG_ID, 
   LOGNAME, 
   CREATORUSERNAME, 
   MODIFIEDDATE, 
   DATASET_ID, 
   DOMAINLOG_ID, 
   LOGTYPE, 
   CREATEDDATE, 
   DESCRIPTION, 
   MODIFIERUSERNAME, 
   ALGORITHMOUTPUT_ID, 
   ALOGRITHM_ID, 
   MASKLOG_ID, 
   REFSTATS_ID, 
   TSARETRAINING_ID, 
   TSGHANDMASK, 
   CUSTOMSCRIPT, 
   BATCHSCRIPT, 
   MIXNUMBER, 
   AUXSPECTRALLAYER_ID, 
   LOCALSPECTRALLAYER_ID, 
   SAMPLECOUNT)
AS
SELECT 
      LOGS.LOG_ID, 
      LOGS.LOGNAME, 
      LOGS.CREATORUSERNAME, 
      CAST(LOGS.MODIFIEDDATE AS datetime), 
      LOGS.DATASET_ID, 
      LOGS.DOMAINLOG_ID, 
      LOGS.LOGTYPE, 
      CAST(LOGS.CREATEDDATE AS datetime), 
      LOGS.DESCRIPTION, 
      LOGS.MODIFIERUSERNAME, 
      LOGS.ALGORITHMOUTPUT_ID, 
      ALGORITHMOUTPUTS.ALGORITHM_ID, 
      LOGS.MASKLOG_ID, 
      LOGS.REFSTATS_ID, 
      LOGS.TSARETRAINING_ID, 
      LOGS.TSGHANDMASK, 
      LOGS.CUSTOMSCRIPT, 
      LOGS.BATCHSCRIPT, 
      LOGS.MIXNUMBER, 
      LOGS.AUXSPECTRALLAYER_ID, 
      LOGS.LOCALSPECTRALLAYER_ID, 
      dbo.GETDATAPOINTS(LOGS.LOG_ID)
   FROM 
      dbo.LOGS 
         INNER JOIN dbo.PUBLISHEDDATASETS 
         ON LOGS.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID 
         LEFT OUTER JOIN dbo.ALGORITHMOUTPUTS 
         ON LOGS.ALGORITHMOUTPUT_ID = ALGORITHMOUTPUTS.ALGORITHMOUTPUT_ID
   WHERE LOGS.ISPUBLIC = 1
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[PUBLISHEDIMAGELOGDATA] (
   LOG_ID, 
   SAMPLENUMBER, 
   IMAGEDATA, 
   IMAGECOMMENT)
AS

   SELECT PUBLISHEDLOGS.LOG_ID, IMAGELOGDATA.SAMPLENUMBER, IMAGELOGDATA.IMAGEDATA, IMAGELOGDATA.IMAGECOMMENT
   FROM 
      dbo.IMAGELOGDATA 
         INNER JOIN dbo.PUBLISHEDLOGS 
         ON IMAGELOGDATA.LOG_ID = PUBLISHEDLOGS.LOG_ID
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEIMAGELOGDATA]  
   @v_imagelog_id varchar(max),
   @v_samplenumber int,
   @v_imagedata varbinary(max),
   @v_imgcomment varchar(max)
AS 

   BEGIN
      IF (@v_imagelog_id IS NOT NULL)
         INSERT dbo.IMAGELOGDATA(LOG_ID, SAMPLENUMBER, IMAGEDATA, IMAGECOMMENT)
            VALUES (@v_imagelog_id, @v_samplenumber, @v_imagedata, @v_imgcomment)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DOMAINLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[UNITS] [varchar](4000) NULL,
	[ISSUBDOMAINOFLOG_ID] [varchar](64) NULL,
	[SCALARGROUP_ID] [int] NULL,
 CONSTRAINT [DOMAINLOGS_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEDOMAINLOG]  
   @v_logid varchar(max),
   @v_units varchar(max),
   @v_issubdomainof varchar(max),
   @v_scalargroupid int
AS 
   
   BEGIN
      UPDATE dbo.DOMAINLOGS
         SET 
            UNITS = @v_units, 
            ISSUBDOMAINOFLOG_ID = @v_issubdomainof, 
            SCALARGROUP_ID = @v_scalargroupid
      WHERE DOMAINLOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEDOMAINLOG]  
   @v_logid varchar(max),
   @v_units varchar(max),
   @v_issubdomainof varchar(max),
   @v_scalargroupid int
AS 

   BEGIN
      INSERT dbo.DOMAINLOGS(LOG_ID, UNITS, ISSUBDOMAINOFLOG_ID, SCALARGROUP_ID)
         VALUES (@v_logid, @v_units, @v_issubdomainof, @v_scalargroupid)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEDOMLOGDATA]  
	@v_domlog_id varchar(max),
	@v_samplenumber int,
	@v_startvalue float(53),
	@v_endvalue float(53),
	@v_name varchar(max),
	@v_desc varchar(max),
	@v_colour int,
	@v_swirminlist varbinary(max),
	@v_swirtsaversion int,
	@v_vnirminlist varbinary(max),
	@v_vnirtsaversion int,
	@v_tirminlist varbinary(max),
	@v_tirtsaversion int
AS 

   BEGIN
      UPDATE dbo.DOMAINLOGDATA
         SET 
            STARTVALUE = @v_startvalue, 
            ENDVALUE = @v_endvalue, 
            SAMPLENAME = @v_name,
			[DESCRIPTION] = @v_desc,
			COLOUR = @v_colour,
			SWIRMINLIST = @v_swirminlist,
			SWIRTSAVERSION = @v_swirtsaversion,
			VNIRMINLIST = @v_vnirminlist,
			VNIRTSAVERSION = @v_vnirtsaversion,
			TIRMINLIST = @v_tirminlist,
			TIRTSAVERSION = @v_tirtsaversion
      WHERE DOMAINLOGDATA.LOG_ID = @v_domlog_id AND DOMAINLOGDATA.SAMPLENUMBER = @v_samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[GETDOMAINDATAFUNC] 
(
	@v_domainlog_id varchar(max)
)
RETURNS 
@Temp TABLE
       (samplenumber   integer INDEX IX1 CLUSTERED,
        startvalue     numeric(18,5),
        endvalue       numeric(18,5),
        samplename     varchar(4000))
AS
BEGIN
	 DECLARE
         @subdomid varchar(64)

      SELECT @subdomid = DOMAINLOGS.ISSUBDOMAINOFLOG_ID
      FROM dbo.DOMAINLOGS
      WHERE DOMAINLOGS.LOG_ID = @v_domainlog_id

      IF (@subdomid IS NULL)
        insert into @Temp SELECT samplenumber,startvalue,endvalue,samplename FROM DOMAINLOGDATA WHERE LOG_ID=@v_domainlog_id ORDER BY samplenumber
      ELSE 
         insert into @Temp SELECT subdom.samplenumber,min(maindom.startvalue) as startvalue,max(maindom.endvalue) as endvalue,subdom.samplename FROM dbo.GETDOMAINDATAFUNC(@subdomid) maindom inner join domainlogdata subdom on maindom.samplenumber BETWEEN subdom.startvalue ANd subdom.endvalue WHERE subdom.log_id=@v_domainlog_id Group BY subdom.samplenumber,subdom.samplename ORDER by subdom.samplenumber
	
	RETURN 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GETDOMAINDATA]
   @v_domainlog_id varchar(max)
AS 
   
   BEGIN
       SELECT * FROM dbo.GETDOMAINDATAFUNC(@v_domainlog_id) order by samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GETDOMAINDATAASBASESPS]
   @v_domainlog_id varchar(max)
AS 
   
   BEGIN

       DECLARE
         @subdomid varchar(64),
		 @subsubdomid varchar(64)

      SELECT @subdomid = DOMAINLOGS.ISSUBDOMAINOFLOG_ID
      FROM dbo.DOMAINLOGS
      WHERE DOMAINLOGS.LOG_ID = @v_domainlog_id

      IF (@subdomid IS not NULL)
		SELECT @subsubdomid = DOMAINLOGS.ISSUBDOMAINOFLOG_ID
        FROM dbo.DOMAINLOGS
        WHERE DOMAINLOGS.LOG_ID = @subdomid

	IF (@subsubdomid IS NULL)
        SELECT samplenumber,startvalue,endvalue,samplename,[description],colour,swirminlist,swirtsaversion,vnirminlist,vnirtsaversion,tirminlist,tirtsaversion,AMINLIST,ATSAVERSION,BMINLIST,BTSAVERSION FROM DOMAINLOGDATA WHERE LOG_ID=@v_domainlog_id ORDER BY samplenumber
      ELSE 
         SELECT subdom.samplenumber,min(maindom.startvalue) as startvalue,max(maindom.endvalue) as endvalue,subdom.samplename,subdom.[description],subdom.colour,subdom.swirminlist,subdom.swirtsaversion,subdom.vnirminlist,subdom.vnirtsaversion,subdom.tirminlist,subdom.tirtsaversion,subdom.AMINLIST,subdom.ATSAVERSION,subdom.BMINLIST,subdom.BTSAVERSION FROM dbo.GETDOMAINDATA(@subdomid) maindom inner join domainlogdata subdom on maindom.samplenumber BETWEEN subdom.startvalue ANd subdom.endvalue WHERE subdom.log_id=@v_domainlog_id Group BY subdom.samplenumber,subdom.samplename,subdom.[description],subdom.colour,subdom.swirminlist,subdom.swirtsaversion,subdom.vnirminlist,subdom.vnirtsaversion,subdom.tirminlist,subdom.tirtsaversion,subdom.AMINLIST,subdom.ATSAVERSION,subdom.BMINLIST,subdom.BTSAVERSION ORDER by subdom.samplenumber
   
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[DOMAINLOGDATAVIEW] (
   LOG_ID, 
   SAMPLENUMBER, 
   STARTVALUE, 
   ENDVALUE, 
   SAMPLENAME)
AS
SELECT TOP 9223372036854775807 WITH TIES 
      subdom.LOG_ID, 
      subdom.SAMPLENUMBER, 
      coalesce(min(maindom.STARTVALUE), min(subdom.STARTVALUE)) AS startvalue, 
      coalesce(max(maindom.ENDVALUE), max(subdom.ENDVALUE)) AS endvalue, 
      subdom.SAMPLENAME
   FROM 
      dbo.DOMAINLOGDATA  AS subdom 
         INNER JOIN dbo.DOMAINLOGS 
         ON subdom.LOG_ID = DOMAINLOGS.LOG_ID 
         LEFT OUTER JOIN dbo.DOMAINLOGDATA  AS maindom 
         ON maindom.LOG_ID = DOMAINLOGS.ISSUBDOMAINOFLOG_ID AND maindom.SAMPLENUMBER BETWEEN subdom.STARTVALUE AND subdom.ENDVALUE
   GROUP BY subdom.LOG_ID, subdom.SAMPLENUMBER, subdom.SAMPLENAME
   ORDER BY subdom.LOG_ID, subdom.SAMPLENUMBER
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEDOMLOGDATA]  
	@v_domlog_id varchar(max),
	@v_samplenumber int,
	@v_startvalue float(53),
	@v_endvalue float(53),
	@v_name varchar(max),
	@v_desc varchar(max),
	@v_colour int,
	@v_swirminlist varbinary(max),
	@v_swirtsaversion int,
	@v_vnirminlist varbinary(max),
	@v_vnirtsaversion int,
	@v_tirminlist varbinary(max),
	@v_tirtsaversion int
AS 
BEGIN
	INSERT dbo.DOMAINLOGDATA(
		LOG_ID, 
		SAMPLENUMBER, 
		STARTVALUE, 
		ENDVALUE, 
		SAMPLENAME,
		[DESCRIPTION],
		COLOUR,
		SWIRMINLIST,
		SWIRTSAVERSION,
		VNIRMINLIST,
		VNIRTSAVERSION,
		TIRMINLIST,
		TIRTSAVERSION)
		VALUES (
			@v_domlog_id, 
			@v_samplenumber, 
			@v_startvalue, 
			@v_endvalue, 
			@v_name,
			@v_desc,
			@v_colour,
			@v_swirminlist,
			@v_swirtsaversion,
			@v_vnirminlist,
			@v_vnirtsaversion,
			@v_tirminlist,
			@v_tirtsaversion)
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATELOGMASK]  
   @v_logid varchar(max),
   @v_maskid varchar(max)
AS 

   BEGIN
      UPDATE dbo.LOGS
         SET 
            MASKLOG_ID = @v_maskid
      WHERE LOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATELOG]  
   @v_logname varchar(max),
   @v_description varchar(max),
   @v_ispublic int,
   @v_logid varchar(max),
   @v_domlogid varchar(max),
   @v_stdalgid int,
   @v_custscript varchar(max),
   @v_masklogid varchar(max),
   @v_refstatsid varchar(max),
   @v_tsaretrainid varchar(max),
   @v_tsghandmask int,
   @v_batchscript varchar(max),
   @v_mixnumber int,
   @v_auxlayerid varchar(max),
   @v_locallayerid varchar(max),
   @v_plsid varchar(max)
AS 

   BEGIN

      DECLARE
         @now datetime
   
      SET @now = GETDATE()


      UPDATE dbo.LOGS
         SET 
            LOGNAME = @v_logname, 
            MODIFIERUSERNAME = session_user, 
            MODIFIEDDATE = @now, 
            DOMAINLOG_ID = @v_domlogid, 
            DESCRIPTION = @v_description, 
            ISPUBLIC = @v_ispublic, 
            ALGORITHMOUTPUT_ID = @v_stdalgid, 
            CUSTOMSCRIPT = @v_custscript, 
            MASKLOG_ID = @v_masklogid, 
            REFSTATS_ID = @v_refstatsid, 
            TSARETRAINING_ID = @v_tsaretrainid, 
            TSGHANDMASK = @v_tsghandmask,
            BATCHSCRIPT = @v_batchscript, 
            MIXNUMBER = @v_mixnumber, 
            LOCALSPECTRALLAYER_ID = @v_locallayerid, 
            AUXSPECTRALLAYER_ID = @v_auxlayerid,
			PLS_ID =@v_plsid
      WHERE LOGS.LOG_ID = @v_logid

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[TOUCHLOG]  
   @v_logid varchar(max),
   @v_usercheckok int  OUTPUT
AS 
   
   BEGIN

      SET @v_usercheckok = NULL

      DECLARE
         @now datetime

      SET @now = GETDATE()

      DECLARE
         @lastmodifier varchar(100)

      SELECT @lastmodifier = LOGS.MODIFIERUSERNAME
      FROM dbo.LOGS
      WHERE LOGS.LOG_ID = @v_logid

      UPDATE dbo.LOGS
         SET 
            MODIFIERUSERNAME = session_user, 
            MODIFIEDDATE = @now
      WHERE LOGS.LOG_ID = @v_logid

      IF (@lastmodifier = session_user)
         SET @v_usercheckok = 1
      ELSE 
         SET @v_usercheckok = 0

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DELETELOG]  
   @v_logid varchar(max),
   @v_success int  OUTPUT,
   @v_overrideusercheck bit = 0
AS 
   
   BEGIN

      DECLARE
         @username varchar(100)

         SET @v_success = NULL

         SELECT @username = LOGS.CREATORUSERNAME
         FROM dbo.LOGS
         WHERE LOGS.LOG_ID = @v_logid

         IF (@username != session_user)
            IF (@v_overrideusercheck = 1)
               BEGIN

                  DELETE dbo.LOGS
                  WHERE LOGS.LOG_ID = @v_logid

                  SET @v_success = @@ROWCOUNT

               END
            ELSE 
               SET @v_success = -1
         ELSE 
            BEGIN

               DELETE dbo.LOGS
               WHERE LOGS.LOG_ID = @v_logid

               SET @v_success = @@ROWCOUNT

            END

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATELOG]  
   @v_dsid varchar(max),
   @v_logid varchar(max),
   @v_logname varchar(max),
   @v_description varchar(max),
   @v_ispublic int,
   @v_logtype int,
   @v_domlogid varchar(max),
   @v_stdalgid int,
   @v_custscript varchar(max),
   @v_masklogid varchar(max),
   @v_refstatsid varchar(max),
   @v_tsaretrainid varchar(max),
   @v_tsghandmask int,
   @v_batchscript varchar(max),
   @v_mixnumber int,
   @v_auxlayerid varchar(max),
   @v_locallayerid varchar(max),
   @v_plsid varchar(max)
AS 
   
   BEGIN

      DECLARE
         @now datetime

      SET @now = GETDATE()


      INSERT dbo.LOGS(
         LOG_ID, 
         LOGNAME, 
         CREATORUSERNAME, 
         MODIFIERUSERNAME, 
         CREATEDDATE, 
         MODIFIEDDATE, 
         DATASET_ID, 
         DOMAINLOG_ID, 
         LOGTYPE, 
         DESCRIPTION, 
         ISPUBLIC, 
         ALGORITHMOUTPUT_ID, 
         CUSTOMSCRIPT, 
         MASKLOG_ID, 
         REFSTATS_ID, 
         TSARETRAINING_ID, 
         TSGHANDMASK, 
         BATCHSCRIPT, 
         MIXNUMBER, 
         LOCALSPECTRALLAYER_ID, 
         AUXSPECTRALLAYER_ID,
         PLS_ID)
         VALUES (
            @v_logid, 
            @v_logname, 
            session_user, 
            session_user, 
            @now, 
            @now, 
            @v_dsid, 
            @v_domlogid, 
            @v_logtype, 
            @v_description, 
            @v_ispublic, 
            @v_stdalgid, 
            @v_custscript, 
            @v_masklogid, 
            @v_refstatsid, 
            @v_tsaretrainid, 
            @v_tsghandmask, 
            @v_batchscript, 
            @v_mixnumber, 
            @v_locallayerid, 
            @v_auxlayerid,
            @v_plsid)

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOGDEPENDENCIES](
	[LOG_ID] [varchar](64) NOT NULL,
	[DEPENDSON] [varchar](64) NULL,
	[RELATIONNAME] [varchar](4000) NOT NULL,
	[PRIORITYORDER] [int] NOT NULL,
 CONSTRAINT [LOGDEPENDENCIES_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[PRIORITYORDER] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATELOGDEP]  
   @v_logid varchar(max),
   @v_dependsonid varchar(max),
   @v_depname varchar(max),
   @v_order int
AS 
   
   BEGIN
      UPDATE dbo.LOGDEPENDENCIES
         SET 
            DEPENDSON = @v_dependsonid, 
            RELATIONNAME = @v_depname, 
            PRIORITYORDER = @v_order
      WHERE LOGDEPENDENCIES.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DELETELOGDEP]  
   @v_logid varchar(max),
   @v_dependson varchar(max)
AS 
   
   BEGIN
      IF (@v_dependson IS NULL)
         DELETE dbo.LOGDEPENDENCIES
         WHERE LOGDEPENDENCIES.LOG_ID = @v_logid
      ELSE 
         DELETE dbo.LOGDEPENDENCIES
         WHERE LOGDEPENDENCIES.LOG_ID = @v_logid AND LOGDEPENDENCIES.DEPENDSON = @v_dependson
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATELOGDEP]  
   @v_logid varchar(max),
   @v_dependsonid varchar(max),
   @v_depname varchar(max),
   @v_order int
AS 

   BEGIN
      INSERT dbo.LOGDEPENDENCIES(LOG_ID, DEPENDSON, RELATIONNAME, PRIORITYORDER)
         VALUES (@v_logid, @v_dependsonid, @v_depname, coalesce(@v_order, 0))
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[MV_LOCATEDSPECIMENS] WITH SCHEMABINDING
AS
SELECT     dbo.DATASETS.DATASET_ID,
			dbo.DATASETS.HOLEIDENTIFIER,
            FLOOR(dbo.DOMAINLOGDATA.STARTVALUE) AS STARTDEPTH,
			FLOOR(dbo.DOMAINLOGDATA.STARTVALUE+1) AS ENDDEPTH,
			LOGS.createddate,
			COUNT_BIG(*) as bcount
FROM       dbo.DATASETS INNER JOIN
            dbo.LOGS ON dbo.DATASETS.DOMAIN_ID = dbo.LOGS.LOG_ID INNER JOIN
            dbo.DOMAINLOGDATA ON dbo.DOMAINLOGDATA.LOG_ID = dbo.LOGS.LOG_ID
WHERE dbo.DATASETS.ispublic =1 and logs.ispublic=1
GROUP BY   dbo.DATASETS.DATASET_ID,
			dbo.DATASETS.HOLEIDENTIFIER,
			FLOOR(dbo.DOMAINLOGDATA.STARTVALUE),
			FLOOR(dbo.DOMAINLOGDATA.STARTVALUE+1),
			LOGS.createddate
GO
SET ARITHABORT ON
GO
SET CONCAT_NULL_YIELDS_NULL ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO
SET ANSI_PADDING ON
GO
SET ANSI_WARNINGS ON
GO
SET NUMERIC_ROUNDABORT OFF
GO
CREATE UNIQUE CLUSTERED INDEX [LOCATEDSPECIMENS_IND] ON [dbo].[MV_LOCATEDSPECIMENS] 
(
	[DATASET_ID] ASC,
	[STARTDEPTH] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[LOCATEDSPECIMENS] WITH SCHEMABINDING
AS
SELECT      'sa.locatedSpecimen.' + DATASET_ID + '.' + CAST(STARTDEPTH AS varchar(64)) + '-' + CAST(ENDDEPTH AS varchar(64)) AS LS_ID,
			DATASET_ID,
			HOLEIDENTIFIER,
            'LINESTRING(' + CAST(STARTDEPTH AS varchar(64)) + ' NaN,' + CAST(ENDDEPTH AS varchar(64)) + ' NaN)' AS GEOM_LINESTRING,
			CONVERT(VARCHAR(10), CREATEDDATE, 120) AS CREATEDDATE,
			startdepth,
			enddepth
FROM         dbo.MV_LOCATEDSPECIMENS WITH (NOEXPAND)
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LAYOUTS](
	[DATASET_ID] [varchar](64) NOT NULL,
	[LAYOUTNO] [int] NOT NULL,
	[LAYOUTDATA] [varchar](max) NULL,
	[LAYOUTNAME] [varchar](4000) NULL,
 CONSTRAINT [LAYOUTS_PK] PRIMARY KEY CLUSTERED 
(
	[DATASET_ID] ASC,
	[LAYOUTNO] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATELAYOUT]  
   @v_dsid varchar(64),
   @v_layoutnumber int,
   @v_layoutdata varchar(max),
   @v_layoutname varchar(max)
AS 
   
   BEGIN
      UPDATE dbo.LAYOUTS SET LAYOUTDATA=@v_layoutdata,LAYOUTNAME=@v_layoutname WHERE DATASET_ID=@v_dsid AND LAYOUTNO=@v_layoutnumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DELETELAYOUT]  
   @v_dsid varchar(64),
   @v_layoutnumber int
AS 
   
   BEGIN
      DELETE FROM dbo.LAYOUTS WHERE DATASET_ID=@v_dsid AND LAYOUTNO=@v_layoutnumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATELAYOUT]  
   @v_dsid varchar(64),
   @v_layoutnumber int,
   @v_layoutdata varchar(max),
   @v_layoutname varchar(max)
AS 
   
   BEGIN

      BEGIN TRY
         INSERT INTO dbo.LAYOUTS (DATASET_ID,LAYOUTNO,LAYOUTDATA,LAYOUTNAME) VALUES (@v_dsid,@v_layoutnumber,@v_layoutdata,@v_layoutname)
      END TRY

      BEGIN CATCH
      
        EXECUTE dbo.UPDATELAYOUT
            @V_DSID= @v_dsid,
            @V_LAYOUTNUMBER=@v_layoutnumber,
            @V_LAYOUTDATA=@v_layoutdata,
            @V_LAYOUTNAME=@v_layoutname
            
      END CATCH
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[ISLOGOWNER] 
( 
   @inlog_id int
)
RETURNS bit
AS 
   
   BEGIN

      DECLARE
         @realownerusername varchar(50)

      SELECT @realownerusername = LOGS.CREATORUSERNAME
      FROM dbo.LOGS
      WHERE LOGS.LOG_ID = @inlog_id

      IF @realownerusername = session_user
	   RETURN 1

	RETURN 0

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CLASSIFICATIONS](
	[ALGORITHMOUTPUT_ID] [int] NOT NULL,
	[INTINDEX] [int] NOT NULL,
	[COLOUR] [int] NOT NULL,
	[CLASSTEXT] [varchar](4000) NOT NULL,
	[DESCRIPTION] [varchar](4000) NULL,
 CONSTRAINT [CLASSIFICATIONS_PK] PRIMARY KEY CLUSTERED 
(
	[ALGORITHMOUTPUT_ID] ASC,
	[INTINDEX] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[MV_COMPOUNDMATERIAL] WITH SCHEMABINDING
AS
SELECT    dbo.LOGS.LOG_ID,
                      FLOOR(dbo.DOMAINLOGDATA.startvalue) AS startdepth,
                      FLOOR(dbo.DOMAINLOGDATA.startvalue+1) AS enddepth,
					  COUNT_BIG(*) AS VALCOUNT, 
                      dbo.CLASSIFICATIONS.CLASSTEXT AS CLASSTEXT, 
                      dbo.CLASSIFICATIONS.COLOUR AS COLOUR
FROM         dbo.LOGS INNER JOIN dbo.DATASETS on dbo.LOGS.dataset_id=dbo.DATASETS.dataset_id
                      INNER JOIN dbo.DOMAINLOGDATA ON dbo.DOMAINLOGDATA.LOG_ID = dbo.LOGS.DOMAINLOG_ID
                      INNER JOIN dbo.CLASSLOGDATA ON dbo.CLASSLOGDATA.LOG_ID = dbo.LOGS.LOG_ID AND dbo.CLASSLOGDATA.CLASSLOGVALUE IS NOT NULL AND 
                      dbo.DOMAINLOGDATA.SAMPLENUMBER = dbo.CLASSLOGDATA.SAMPLENUMBER 
                      INNER JOIN dbo.ALGORITHMOUTPUTS ON dbo.ALGORITHMOUTPUTS.ALGORITHMOUTPUT_ID = dbo.LOGS.ALGORITHMOUTPUT_ID
                      INNER JOIN dbo.CLASSIFICATIONS ON dbo.ALGORITHMOUTPUTS.ALGORITHMOUTPUT_ID = dbo.CLASSIFICATIONS.ALGORITHMOUTPUT_ID AND 
                      dbo.CLASSLOGDATA.CLASSLOGVALUE = dbo.CLASSIFICATIONS.INTINDEX
WHERE        dbo.DATASETS.ispublic=1 and LOGS.ISPUBLIC = 1 and dbo.LOGS.ALGORITHMOUTPUT_ID IN ( 1, 12, 32, 40, 48, 74, 88, 7, 20, 26, 82, 96)
GROUP BY     dbo.LOGS.LOG_ID,
                      FLOOR(dbo.DOMAINLOGDATA.startvalue),
                      FLOOR(dbo.DOMAINLOGDATA.startvalue+1),
                      dbo.CLASSIFICATIONS.CLASSTEXT,
                      dbo.CLASSIFICATIONS.COLOUR;
GO
SET ARITHABORT ON
GO
SET CONCAT_NULL_YIELDS_NULL ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_NULLS ON
GO
SET ANSI_PADDING ON
GO
SET ANSI_WARNINGS ON
GO
SET NUMERIC_ROUNDABORT OFF
GO
CREATE UNIQUE CLUSTERED INDEX [COMPOUNDMATERIAL_IND] ON [dbo].[MV_COMPOUNDMATERIAL] 
(
	[LOG_ID] ASC,
	[CLASSTEXT] ASC,
	[startdepth] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[COMPOUNDMATERIAL] WITH SCHEMABINDING
AS
SELECT    'constituentpart.' + LOG_ID + '.' + CAST(STARTDEPTH AS varchar(64)) + '-' 
			+ CAST(ENDDEPTH AS varchar(64)) + '#' + CLASSTEXT AS LOGINTERVALSCAL_ID,
            'om.observations.' + LOG_ID + '.' + CAST(STARTDEPTH AS varchar(64)) 
            + '-' + CAST(ENDDEPTH AS varchar(64)) AS LOGINTERVAL_ID,
            LOG_ID,
            startdepth,
			enddepth,
			VALCOUNT,
			CLASSTEXT,
			COLOUR
FROM         dbo.MV_COMPOUNDMATERIAL WITH (NOEXPAND)
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CLASSSPECIFICCLASSIFICATIONS](
	[LOG_ID] [varchar](64) NOT NULL,
	[INTINDEX] [int] NOT NULL,
	[COLOUR] [int] NOT NULL,
	[CLASSTEXT] [varchar](4000) NULL,
	[DESCRIPTION] [varchar](4000) NULL,
 CONSTRAINT [CLASSIFICATIONSCOLOUROVER_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC,
	[INTINDEX] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATECLASSSPECCLASSIFICATION]  
   @v_classlog_id varchar(max),
   @v_intind int,
   @v_colour int,
   @v_text varchar(max),
   @v_description varchar(max)
AS 
   
   BEGIN
      UPDATE dbo.CLASSSPECIFICCLASSIFICATIONS
         SET 
            COLOUR = @v_colour, 
            CLASSTEXT = @v_text, 
            DESCRIPTION = @v_description
      WHERE CLASSSPECIFICCLASSIFICATIONS.LOG_ID = @v_classlog_id AND CLASSSPECIFICCLASSIFICATIONS.INTINDEX = @v_intind
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DELETEALLCLASSIFICATIONS]  
   @v_logid varchar(max)
AS 

   BEGIN

      DELETE dbo.CLASSSPECIFICCLASSIFICATIONS
         WHERE CLASSSPECIFICCLASSIFICATIONS.LOG_ID = @v_logid

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATECLASSSPECCLASSIFICATION]  
   @v_classlog_id varchar(max),
   @v_intind int,
   @v_colour int,
   @v_text varchar(max),
   @v_description varchar(max)
AS 

   BEGIN
      INSERT dbo.CLASSSPECIFICCLASSIFICATIONS(
         LOG_ID, 
         INTINDEX, 
         COLOUR, 
         CLASSTEXT, 
         DESCRIPTION)
         VALUES (
            @v_classlog_id, 
            @v_intind, 
            @v_colour, 
            @v_text, 
            @v_description)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CLASSLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
	[SCALARGROUP_ID] [int] NULL,
 CONSTRAINT [CLASSLOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATECLASSLOG]  
   @v_logid varchar(max),
   @v_scalgroupid int
AS 

   BEGIN
      UPDATE dbo.CLASSLOGS
         SET 
            SCALARGROUP_ID = @v_scalgroupid
      WHERE CLASSLOGS.LOG_ID = @v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATECLASSLOG]  
   @v_logid varchar(max),
   @v_scalgroupid int
AS 
   
   BEGIN
      INSERT dbo.CLASSLOGS(LOG_ID, SCALARGROUP_ID)
         VALUES (@v_logid, @v_scalgroupid)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATECLASSLOGDATA]  
   @v_classlog_id varchar(max),
   @v_samplenumber int,
   @v_classvalue int
AS 
   
   BEGIN
      UPDATE dbo.CLASSLOGDATA
         SET 
            CLASSLOGVALUE = @v_classvalue
      WHERE CLASSLOGDATA.LOG_ID = @v_classlog_id AND CLASSLOGDATA.SAMPLENUMBER = @v_samplenumber
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GETLOGEXTENTS]  
   @v_logid varchar(max),
   @v_minval int  OUTPUT,
   @v_maxval int  OUTPUT
AS 
   
   BEGIN

      DECLARE
         @curlogtype int

         SET @v_minval = NULL

         SET @v_maxval = NULL

         SELECT @curlogtype = LOGS.LOGTYPE
         FROM dbo.LOGS
         WHERE LOGS.LOG_ID = @v_logid

         IF @curlogtype = 0
            BEGIN

               SELECT @v_minval = min(DOMAINLOGDATA.STARTVALUE), @v_maxval = max(DOMAINLOGDATA.ENDVALUE)
               FROM dbo.DOMAINLOGDATA
               WHERE DOMAINLOGDATA.LOG_ID = @v_logid

            END
         ELSE 
            IF @curlogtype = 1
               BEGIN

                  SELECT @v_minval = min(CLASSLOGDATA.CLASSLOGVALUE), @v_maxval = max(CLASSLOGDATA.CLASSLOGVALUE)
                  FROM dbo.CLASSLOGDATA
                  WHERE CLASSLOGDATA.LOG_ID = @v_logid

               END
            ELSE 
               IF @curlogtype = 2
                  BEGIN

                     SELECT @v_minval = min(DECIMALLOGDATA.DECIMALVALUE), @v_maxval = max(DECIMALLOGDATA.DECIMALVALUE)
                     FROM dbo.DECIMALLOGDATA
                     WHERE DECIMALLOGDATA.LOG_ID = @v_logid

                  END
               ELSE 
                  BEGIN

                     SET @v_minval = 0

                     SET @v_maxval = 0

                  END

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATECLASSLOGDATA]  
   @v_classlog_id varchar(max),
   @v_samplenumber int,
   @v_classvalue int
AS 

   BEGIN
      INSERT dbo.CLASSLOGDATA(LOG_ID, SAMPLENUMBER, CLASSLOGVALUE)
         VALUES (@v_classlog_id, @v_samplenumber, @v_classvalue)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATEDATASET]  
   @v_ds_id varchar(max),
   @v_dsname varchar(max),
   @v_extdhservicename varchar(max),
   @v_extdhid varchar(max),
   @v_tsglayout varchar(max),
   @v_isreflib bit,
   @v_domlogid varchar(max),
   @v_primarymachineid int,
   @v_mainspectrallogid varchar(max),
   @v_mainimagelogid varchar(max),
   @v_mainproflogid varchar(max),
   @v_traylogid varchar(max),
   @v_sectionlogid varchar(max),
   @v_dsdescription varchar(max),
   @v_origauthor varchar(max),
   @v_scantime datetime,
   @v_importtime datetime,
   @v_customdsid varchar(max),
   @v_custcalcdsid varchar(max),
   @v_ispublic bit,
   @v_chk_l0archived int,
   @v_chk_fmstatus int,
   @v_chk_tidlstatus int,
   @v_chk_imgstatus int,
   @v_chk_sutsa int,
   @v_chk_vutsa int,
   @v_chk_tutsa int,
   @v_chk_imports int,
   @v_chk_rmarks int,
   @v_chk_batstatus int,
   @v_chk_sclrstatus int,
   @v_chk_domains int,
   @v_chk_plots int,
   @v_chk_layouts int,
   @v_chk_dbase int
AS 
  
   BEGIN

      DECLARE
         @tmpisreflib int,
         @tmpispublic int,
         @now datetime

    	SET @now = GETDATE()

      IF (@v_isreflib != 0)
         SET @tmpisreflib = 1
      ELSE 
         SET @tmpisreflib = 0

      IF (@v_ispublic != 0)
         SET @tmpispublic = 1
      ELSE 
         SET @tmpispublic = 0

      UPDATE dbo.DATASETS
         SET 
            DATASETNAME = @v_dsname, 
            DOMAIN_ID = @v_domlogid, 
            MODIFIEDDATE = @now, 
            MODIFIERUSERNAME = session_user, 
            TSGLAYOUT = @v_tsglayout, 
            ISREFERENCELIBRARY = @tmpisreflib, 
            PRIMARYLOGGER_ID = @v_primarymachineid, 
            SPECLOG_ID = @v_mainspectrallogid, 
            IMAGELOG_ID = @v_mainimagelogid, 
            PROFLOG_ID = @v_mainproflogid, 
            TRAYLOG_ID = @v_traylogid, 
            SECTIONLOG_ID = @v_sectionlogid, 
            HOLEDATASOURCENAME = @v_extdhservicename, 
            HOLEIDENTIFIER = @v_extdhid, 
            DSDESCRIPTION = @v_dsdescription, 
            ORIGAUTHOR = @v_origauthor, 
            SCANDATE = @v_scantime, 
            IMPORTDATE = @v_importtime, 
            CUSTOMDATASET_ID = @v_customdsid, 
            CUSTCALCDATASET_ID = @v_custcalcdsid, 
            ISPUBLIC = @tmpispublic,
			CHK_L0ARCHIVED = @v_chk_l0archived,
			CHK_FMSTATUS = @v_chk_fmstatus,
			CHK_TIDLSTATUS = @v_chk_tidlstatus,
			CHK_IMGSTATUS = @v_chk_imgstatus,
			CHK_SUTSA = @v_chk_sutsa,
			CHK_VUTSA = @v_chk_vutsa,
			CHK_TUTSA = @v_chk_tutsa,
			CHK_IMPORTS = @v_chk_imports,
			CHK_RMARKS = @v_chk_rmarks,
			CHK_BATSTATUS = @v_chk_batstatus,
			CHK_SCLRSTATUS = @v_chk_sclrstatus,
			CHK_DOMAINS = @v_chk_domains,
			CHK_PLOTS = @v_chk_plots,
			CHK_LAYOUTS = @v_chk_layouts,
			CHK_DBASE = @v_chk_dbase
      WHERE DATASETS.DATASET_ID = @v_ds_id

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[TRAYIMAGESANDDETAILSVIEW] (
   Dataset_ID, 
   [Tray Number], 
   [Start Depth], 
   [End Depth], 
   [Tray Image])
AS

SELECT TOP 9223372036854775807 WITH TIES
	depths.DATASET_ID Dataset_ID, 
      cast(CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT as int) "Tray Number", 
	  depths.STARTVALUE, 
      depths.ENDVALUE,
	  IMAGELOGDATA.IMAGEDATA "Tray Image"
FROM (SELECT TOP 9223372036854775807 WITH TIES 
		DATASETS.DATASET_ID Dataset_ID, 
		traydomdata.samplenumber samplenumber,
		min(sampdomdata.STARTVALUE) startvalue, 
		max(sampdomdata.ENDVALUE) endvalue,
		trayimagelog.log_id imagelogid
	FROM dbo.DATASETS 
		INNER JOIN dbo.LOGS trayimagelog
        ON trayimagelog.DATASET_ID = DATASETS.DATASET_ID AND trayimagelog.LOGTYPE = 3 AND trayimagelog.LOGNAME = 'Tray Images' 
		inner join dbo.domainlogs traydom
		on traydom.log_id=trayimagelog.domainlog_id		
		inner join dbo.DOMAINLOGDATA traydomdata
		on traydomdata.log_id=traydom.log_id
		inner join dbo.domainlogs sampdom
		ON sampdom.log_id=traydom.issubdomainoflog_id
        INNER JOIN dbo.DOMAINLOGDATA sampdomdata 
        ON sampdomdata.log_id=sampdom.log_id and sampdomdata.SAMPLENUMBER between traydomdata.STARTVALUE and traydomdata.ENDVALUE
	GROUP BY DATASETS.DATASET_ID,traydomdata.samplenumber,trayimagelog.log_id
	ORDER BY DATASETS.DATASET_ID, traydomdata.samplenumber) depths
	inner join dbo.datasets
	on depths.dataset_id=datasets.dataset_id
	INNER JOIN dbo.CLASSLOGDATA 
    ON CLASSLOGDATA.LOG_ID = DATASETS.TRAYLOG_ID AND CLASSLOGDATA.SAMPLENUMBER = depths.SAMPLENUMBER and CLASSLOGDATA.classlogvalue>=0
    INNER JOIN dbo.CLASSSPECIFICCLASSIFICATIONS 
    ON CLASSSPECIFICCLASSIFICATIONS.LOG_ID = CLASSLOGDATA.LOG_ID AND CLASSSPECIFICCLASSIFICATIONS.INTINDEX = CLASSLOGDATA.CLASSLOGVALUE 
	inner JOIN dbo.IMAGELOGDATA 
    ON IMAGELOGDATA.LOG_ID = depths.imageLOGID AND IMAGELOGDATA.SAMPLENUMBER = depths.SAMPLENUMBER
	WHERE CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT is not null
	ORDER BY DATASETS.DATASET_ID, cast(CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT as int)
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[SAMPLINGFEATURECOLLECTIONS]
AS
SELECT     DATASET_ID,'sa.samplingFeatureCollection.'+ DATASET_ID GML_ID,'urn:ogc:feature:SamplingFeatureCollection:SamplingFeatureCollection.'
           + datasets.DATASET_ID GML_NAME, HOLEIDENTIFIER
FROM         dbo.DATASETS WHERE ispublic =1;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEDATASET]  
   @v_dsid varchar(max),
   @v_dsname varchar(max),
   @v_extdhservicename varchar(max),
   @v_extdhid varchar(max),
   @v_tsglayout varchar(max),
   @v_isreflib bit,
   @v_scantime datetime,
   @v_importtime datetime,
   @v_origauthor varchar(max),
   @v_ispublic int,
   @retid varchar(64) OUTPUT
AS 
   
   BEGIN

      DECLARE

         @tmpisreflib int, 
         @now datetime

      SET @now = GETDATE()

      IF (@v_isreflib != 0)
         SET @tmpisreflib = 1
      ELSE 
         SET @tmpisreflib = 0

      INSERT dbo.DATASETS(
         DATASET_ID, 
         DATASETNAME, 
         HOLEDATASOURCENAME, 
         HOLEIDENTIFIER, 
         TSGLAYOUT, 
         ISREFERENCELIBRARY, 
         CREATEDDATE, 
         MODIFIEDDATE, 
         CREATORUSERNAME, 
         MODIFIERUSERNAME, 
         ORIGAUTHOR, 
         SCANDATE, 
         IMPORTDATE, 
         ISPUBLIC)
         VALUES (
            @v_dsid, 
            @v_dsname, 
            @v_extdhservicename, 
            @v_extdhid, 
            @v_tsglayout, 
            @tmpisreflib, 
            @now, 
            @now, 
            session_user, 
            session_user, 
            @v_origauthor, 
            @v_scantime, 
            @v_importtime, 
            @v_ispublic)

	SELECT @retid=@v_dsid

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[DATASETDETAILSVIEW] (
   Dataset_ID, 
   [Dataset Name], 
   [Drillhole Identifier], 
   [Scanned Date], 
   [Processed Date])
AS 

   SELECT 
      DATASETS.DATASET_ID, 
      DATASETS.DATASETNAME, 
      DATASETS.HOLEIDENTIFIER, 
      DATASETS.SCANDATE, 
      DATASETS.IMPORTDATE
   FROM dbo.DATASETS
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[CALIBRATIONLOGS](
	[LOG_ID] [varchar](64) NOT NULL,
 CONSTRAINT [CALIBRATIONLOG_PK] PRIMARY KEY CLUSTERED 
(
	[LOG_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATECALIBRATIONLOG]  
   @v_logid varchar(max)
AS 
   
   BEGIN
      UPDATE dbo.CALIBRATIONLOGS SET LOG_ID=@v_logid WHERE LOG_ID=@v_logid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATECALIBRATIONLOG]  
   @v_logid varchar(max)
AS 
   
   BEGIN
      INSERT dbo.CALIBRATIONLOGS(LOG_ID) VALUES (@v_logid)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATECALIBRATIONLOGDATA]  
   @v_calibrationlog_id varchar(max),
   @v_samplenumber int,
   @v_calibrationdata varbinary(max)
AS 

   BEGIN
      UPDATE dbo.CALIBRATIONLOGDATA SET SAMPLENUMBER=@v_samplenumber, CALIBRATIONDATA=@v_calibrationdata WHERE LOG_ID=@v_calibrationlog_id
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GETLOGDATAPOINTCOUNT]  
   @v_logid varchar(max),
   @datapointcount int  OUTPUT
AS 
   BEGIN

      DECLARE
         @curlogtype int

         SET @datapointcount = NULL

         SELECT @curlogtype = LOGS.LOGTYPE
         FROM dbo.LOGS
         WHERE LOGS.LOG_ID = @v_logid

         IF @curlogtype = 0
            BEGIN

               SELECT @datapointcount = count_big(*)
               FROM dbo.DOMAINLOGDATA
               WHERE DOMAINLOGDATA.LOG_ID = @v_logid

            END
         ELSE 
            IF @curlogtype = 1
               BEGIN

                  SELECT @datapointcount = count_big(*)
                  FROM dbo.CLASSLOGDATA
                  WHERE CLASSLOGDATA.LOG_ID = @v_logid

               END
            ELSE 
               IF @curlogtype = 2
                  BEGIN

                     SELECT @datapointcount = count_big(*)
                     FROM dbo.DECIMALLOGDATA
                     WHERE DECIMALLOGDATA.LOG_ID = @v_logid

                  END
               ELSE 
                  IF @curlogtype = 3
                     BEGIN

                        SELECT @datapointcount = count_big(*)
                        FROM dbo.IMAGELOGDATA
                        WHERE IMAGELOGDATA.LOG_ID = @v_logid

                     END
                  ELSE 
                     IF @curlogtype = 4
                        BEGIN

                           SELECT @datapointcount = count_big(*)
                           FROM dbo.PROFLOGDATA
                           WHERE PROFLOGDATA.LOG_ID = @v_logid

                        END
                     ELSE 
                        IF @curlogtype = 5
                           BEGIN

                              SELECT @datapointcount = count_big(*)
                              FROM dbo.SPECTRALLOGDATA
                              WHERE SPECTRALLOGDATA.LOG_ID = @v_logid

                           END
                        ELSE 
                           IF @curlogtype = 6
                              BEGIN

                                 SELECT @datapointcount = count_big(*)
                                 FROM dbo.MASKLOGDATA
                                 WHERE MASKLOGDATA.LOG_ID = @v_logid

                              END
                           ELSE 
                              IF @curlogtype = 7
                                 BEGIN

                                    SELECT @datapointcount = count_big(*)
                                    FROM dbo.CALIBRATIONLOGDATA
                                    WHERE CALIBRATIONLOGDATA.LOG_ID = @v_logid

                                 END


   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DELETEALLLOGDATA]  
   @v_logid varchar(max)
AS 
   
   BEGIN

      DECLARE
         @curlogtype int

       SELECT @curlogtype = LOGS.LOGTYPE
         FROM dbo.LOGS
         WHERE LOGS.LOG_ID = @v_logid

         IF @curlogtype = 0
            DELETE dbo.DOMAINLOGDATA
            WHERE DOMAINLOGDATA.LOG_ID = @v_logid
         ELSE 
            IF @curlogtype = 1
               DELETE dbo.CLASSLOGDATA
               WHERE CLASSLOGDATA.LOG_ID = @v_logid
            ELSE 
               IF @curlogtype = 2
                  DELETE dbo.DECIMALLOGDATA
                  WHERE DECIMALLOGDATA.LOG_ID = @v_logid
               ELSE 
                  IF @curlogtype = 3
                     DELETE dbo.IMAGELOGDATA
                     WHERE IMAGELOGDATA.LOG_ID = @v_logid
                  ELSE 
                     IF @curlogtype = 4
                        DELETE dbo.PROFLOGDATA
                        WHERE PROFLOGDATA.LOG_ID = @v_logid
                     ELSE 
                        IF @curlogtype = 5
                           DELETE dbo.SPECTRALLOGDATA
                           WHERE SPECTRALLOGDATA.LOG_ID = @v_logid
                        ELSE 
                           IF @curlogtype = 6
                              DELETE dbo.MASKLOGDATA
                              WHERE MASKLOGDATA.LOG_ID = @v_logid
                           ELSE 
                              IF @curlogtype = 7
                                 DELETE dbo.CALIBRATIONLOGDATA
                                 WHERE CALIBRATIONLOGDATA.LOG_ID = @v_logid


   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATECALIBRATIONLOGDATA]  
   @v_calibrationlog_id varchar(max),
   @v_samplenumber int,
   @v_calibrationdata varbinary(max)
AS 

   BEGIN
      INSERT dbo.CALIBRATIONLOGDATA(LOG_ID, SAMPLENUMBER, CALIBRATIONDATA)
         VALUES (@v_calibrationlog_id, @v_samplenumber, @v_calibrationdata)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ALGORITHMS](
	[ALGORITHM_ID] [int] NOT NULL,
	[ALGORITHMNAME] [varchar](4000) NULL,
	[CREATORUSERNAME] [varchar](4000) NULL,
	[ISPUBLIC] [numeric](1, 0) NOT NULL,
	[DESCRIPTION] [varchar](4000) NULL,
	[CREATEDDATE] [datetime] NULL,
 CONSTRAINT [ALGORITHMS_PK] PRIMARY KEY CLUSTERED 
(
	[ALGORITHM_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SCALARGROUPS](
	[SCALARGROUP_ID] [int] NOT NULL,
	[SCALARGROUPNAME] [varchar](4000) NULL,
	[DESCRIPTION] [varchar](4000) NULL,
	[PARENTSCALARGROUP_ID] [int] NULL,
 CONSTRAINT [SCALARGROUPS_PK] PRIMARY KEY CLUSTERED 
(
	[SCALARGROUP_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PLSDATA](
	[PLS_ID] [varchar](64) NOT NULL,
	[DATASET_ID] [varchar](64) NOT NULL,
	[PLSNAME] [varchar](4000) NOT NULL,
	[PLSDATA] [varbinary](max) NOT NULL,
 CONSTRAINT [PK_PLSD] PRIMARY KEY CLUSTERED 
(
	[PLS_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[SEARCHFORDATASETS] (
   DATASETNAME, 
   DATASET_ID, 
   HOLEIDENTIFIER, 
   MODIFIEDDATE, 
   CREATEDDATE, 
   CREATORUSERNAME, 
   MODIFIERUSERNAME, 
   MACHINENAME, 
   TRAYCOUNT, 
   SAMPLECOUNT)
AS

   SELECT 
      DATASETS.DATASETNAME, 
      DATASETS.DATASET_ID, 
      DATASETS.HOLEIDENTIFIER, 
      DATASETS.MODIFIEDDATE, 
      DATASETS.CREATEDDATE, 
      DATASETS.CREATORUSERNAME, 
      DATASETS.MODIFIERUSERNAME, 
      MACHINES.MACHINENAME, 
      dbo.getdatapoints(DATASETS.TRAYLOG_ID) AS traycount,    
      dbo.getdatapoints(DATASETS.DOMAIN_ID) AS samplecount   
   FROM 
      dbo.DATASETS 
         LEFT JOIN dbo.MACHINES 
         ON DATASETS.PRIMARYLOGGER_ID = MACHINES.MACHINE_ID
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[LOGTYPES](
	[LOGTYPE_ID] [int] NOT NULL,
	[LOGTYPENAME] [varchar](20) NULL,
 CONSTRAINT [LOGTYPES_PK] PRIMARY KEY CLUSTERED 
(
	[LOGTYPE_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[DATASETSTATS](
	[DATASETSTATS_ID] [varchar](64) NOT NULL,
	[VALIDBITMASK] [bigint] NULL,
	[STATSTYPE] [numeric](1, 0) NULL,
	[SAMPLES] [int] NULL,
	[LIBCHANNELS] [int] NULL,
	[CHANNELS] [int] NULL,
	[WSUB] [int] NULL,
	[WUFLAGS] [bigint] NULL,
	[LCWHICH] [bigint] NULL,
	[LCPRENORM] [bigint] NULL,
	[LCBKREM] [bigint] NULL,
	[LCPOSTNORM] [bigint] NULL,
	[LCSGLEFT] [bigint] NULL,
	[LCSGRIGHT] [bigint] NULL,
	[LCSGPOLY] [bigint] NULL,
	[LCSGDERIV] [bigint] NULL,
	[RESAMPLINGALGORITHM] [bigint] NULL,
	[RESWMIN] [float] NULL,
	[RESWMAX] [float] NULL,
	[RESWINC] [float] NULL,
	[WMIN] [float] NULL,
	[WMAX] [float] NULL,
	[WTOL] [float] NULL,
	[LIBRARYDATASET_ID] [varchar](64) NULL,
	[MASKLOG_ID] [varchar](64) NULL,
	[USERCOMMENTS] [varchar](4000) NULL,
	[WVL] [varbinary](max) NULL,
	[CHMAX] [varbinary](max) NULL,
	[CHMIN] [varbinary](max) NULL,
	[MEAN] [varbinary](max) NULL,
	[SDEV] [varbinary](max) NULL,
	[COVAR] [varbinary](max) NULL,
	[CORREL] [varbinary](max) NULL,
	[NOISECV] [varbinary](max) NULL,
	[EVAL] [varbinary](max) NULL,
	[FTRANS] [varbinary](max) NULL,
	[RTRANS] [varbinary](max) NULL,
	[LIB_WVL] [varbinary](max) NULL,
 CONSTRAINT [DATASETSTATS_PK] PRIMARY KEY CLUSTERED 
(
	[DATASETSTATS_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TSARETRAINING](
	[TSARETRAINING_ID] [varchar](64) NOT NULL,
	[NUMALLCLASSES] [int] NULL,
	[NUMTRAINCLASSES] [int] NULL,
	[TSAPARAMS] [varbinary](max) NULL,
	[MINMASK] [varbinary](max) NULL,
	[MIXMASK] [varbinary](max) NULL,
	[DBIX] [numeric](3, 0) NULL,
	[TRAINSEL] [int] NULL,
	[DOMAINED] [int] NULL,
	[VERSION] [int] NULL,
	[ILLITE] [int] NULL,
	[SEVEN] [int] NULL,
	[PLUS] [int] NULL,
 CONSTRAINT [TSARETRAINING_PK] PRIMARY KEY CLUSTERED 
(
	[TSARETRAINING_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[UPDATETSARETRAINING]  
   @v_numallclasses int,
   @v_numtrainclasses int,
   @v_tsaparams varbinary(max),
   @v_minmask varbinary(max),
   @v_mixmask varbinary(max),
   @v_tsaretrainid varchar(max),
   @v_dbix int,
   @v_trainsel int,
   @v_illite int,
   @v_seven int,
   @v_domained int,
   @v_version int,
   @v_plus int
AS 

   BEGIN
      UPDATE dbo.TSARETRAINING
         SET 
            NUMALLCLASSES = @v_numallclasses, 
            NUMTRAINCLASSES = @v_numtrainclasses, 
            TSAPARAMS = @v_tsaparams, 
            MINMASK = @v_minmask, 
            MIXMASK = @v_mixmask, 
            DBIX = @v_dbix,
            TRAINSEL = @v_trainsel,
			ILLITE = @v_illite,
			SEVEN = @v_seven,
			DOMAINED = @v_domained,
			VERSION = @v_version,
			PLUS = @v_plus
      WHERE TSARETRAINING.TSARETRAINING_ID = @v_tsaretrainid
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEPLSDATA] 
	@v_plsid varchar(64),
   @v_dsid varchar(64),
   @v_plsname varchar(max),
   @v_plsdata varbinary(max)
AS
BEGIN

	SET NOCOUNT ON;

	INSERT INTO PLSDATA (PLS_ID,DATASET_ID,PLSNAME,PLSDATA) VALUES (@v_plsid,@v_dsid,@v_plsname,@v_plsdata)  

END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATEDATASETSTATS]  
   @v_datasetstats_id varchar(max),
   @v_validbitmask int,
   @v_statstype int,
   @v_samples int,
   @v_libchannels int,
   @v_channels int,
   @v_wsub int,
   @v_wuflags int,
   @v_lcwhich int,
   @v_lcprenorm int,
   @v_lcbkrem int,
   @v_lcpostnorm int,
   @v_lcsgleft int,
   @v_lcsgright int,
   @v_lcsgpoly int,
   @v_lcsgderiv int,
   @v_resamplingalgorithm int,
   @v_reswmin float(53),
   @v_reswmax float(53),
   @v_reswinc float(53),
   @v_wmin float(53),
   @v_wmax float(53),
   @v_wtol float(53),
   @v_librarydataset_id varchar(max),
   @v_masklog_id varchar(max),
   @v_usercomments varchar(max),
   @v_wvl varbinary(max),
   @v_lib_wvl varbinary(max),
   @v_chmax varbinary(max),
   @v_chmin varbinary(max),
   @v_mean varbinary(max),
   @v_sdev varbinary(max),
   @v_covar varbinary(max),
   @v_correl varbinary(max),
   @v_noisecv varbinary(max),
   @v_eval varbinary(max),
   @v_ftrans varbinary(max),
   @v_rtrans varbinary(max)
AS 
   
   BEGIN
      INSERT dbo.DATASETSTATS(
         DATASETSTATS_ID, 
         VALIDBITMASK, 
         STATSTYPE, 
         SAMPLES, 
         LIBCHANNELS, 
         CHANNELS, 
         WSUB, 
         WUFLAGS, 
         LCWHICH, 
         LCPRENORM, 
         LCBKREM, 
         LCPOSTNORM, 
         LCSGLEFT, 
         LCSGRIGHT, 
         LCSGPOLY, 
         LCSGDERIV, 
         RESAMPLINGALGORITHM, 
         RESWMIN, 
         RESWMAX, 
         RESWINC, 
         WMIN, 
         WMAX, 
         WTOL, 
         LIBRARYDATASET_ID, 
         MASKLOG_ID, 
         USERCOMMENTS, 
         WVL, 
         CHMAX, 
         CHMIN, 
         MEAN, 
         SDEV, 
         COVAR, 
         CORREL, 
         NOISECV, 
         EVAL, 
         FTRANS, 
         RTRANS, 
         LIB_WVL)
         VALUES (
            @v_datasetstats_id, 
            @v_validbitmask, 
            @v_statstype, 
            @v_samples, 
            @v_libchannels, 
            @v_channels, 
            @v_wsub, 
            @v_wuflags, 
            @v_lcwhich, 
            @v_lcprenorm, 
            @v_lcbkrem, 
            @v_lcpostnorm, 
            @v_lcsgleft, 
            @v_lcsgright, 
            @v_lcsgpoly, 
            @v_lcsgderiv, 
            @v_resamplingalgorithm, 
            @v_reswmin, 
            @v_reswmax, 
            @v_reswinc, 
            @v_wmin, 
            @v_wmax, 
            @v_wtol, 
            @v_librarydataset_id, 
            @v_masklog_id, 
            @v_usercomments, 
            @v_wvl, 
            @v_chmax, 
            @v_chmin, 
            @v_mean, 
            @v_sdev, 
            @v_covar, 
            @v_correl, 
            @v_noisecv, 
            @v_eval, 
            @v_ftrans, 
            @v_rtrans, 
            @v_lib_wvl)
   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[ISALGORITHMOWNER] 
( 
   @inalg_id int
)
RETURNS bit
AS 
   
   BEGIN

      DECLARE
         @realownerusername varchar(50)

      SELECT @realownerusername = ALGORITHMS.CREATORUSERNAME
      FROM dbo.ALGORITHMS
      WHERE ALGORITHMS.ALGORITHM_ID = @inalg_id

      IF @realownerusername = session_user
         RETURN 1

      RETURN 0

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[DELETEPLSDATA] 
	@v_plsid varchar(64)
AS
BEGIN

	SET NOCOUNT ON;
	IF EXISTS(SELECT pls_id FROM plsdata WHERE pls_id = @v_plsid)
	BEGIN
		DELETE FROM plsdata WHERE pls_id = @v_plsid
	END
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[PUBLISHEDALGORITHMS] (
   DATASET_ID, 
   ALGORITHM_ID, 
   ALGORITHMNAME, 
   DESCRIPTION, 
   SCANDATE)
AS 
   
   /*
   *   Generated by SQL Server Migration Assistant for Oracle.
   *   Contact ora2sql@microsoft.com or visit http://www.microsoft.com/sql/migration for more information.
   */
   SELECT DISTINCT 
      PUBLISHEDLOGS.DATASET_ID, 
      ALGORITHMOUTPUTS.ALGORITHM_ID, 
      ALGORITHMS.ALGORITHMNAME, 
      ALGORITHMS.DESCRIPTION, 
      PUBLISHEDDATASETS.SCANDATE
   FROM 
      dbo.PUBLISHEDDATASETS 
         INNER JOIN dbo.PUBLISHEDLOGS 
         ON PUBLISHEDDATASETS.DATASET_ID = PUBLISHEDLOGS.DATASET_ID 
         INNER JOIN dbo.ALGORITHMOUTPUTS 
         ON PUBLISHEDLOGS.ALGORITHMOUTPUT_ID = ALGORITHMOUTPUTS.ALGORITHMOUTPUT_ID 
         INNER JOIN dbo.ALGORITHMS 
         ON ALGORITHMOUTPUTS.ALGORITHM_ID = ALGORITHMS.ALGORITHM_ID
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[OBSERVATIONS] (
   LS_ID, 
   LOG_ID, 
   LOGNAME, 
   CREATEDDATE,
   results)
AS
SELECT 
      LOCATEDSPECIMENS.LS_ID, 
            'om.observations.'+LOGS.LOG_ID + '.' + CAST(LOCATEDSPECIMENS.STARTDEPTH AS varchar(20)) + '-' + CAST(LOCATEDSPECIMENS.ENDDEPTH AS varchar(20)), 
      LOGS.LOGNAME,  
      CONVERT(VARCHAR(10), LOGS.CREATEDDATE, 120),
      count(COMPOUNDMATERIAL.loginterval_id)
   FROM 
      dbo.LOGS 
         INNER JOIN dbo.DATASETS
         ON LOGS.DATASET_ID = DATASETS.DATASET_ID 
         INNER JOIN dbo.LOCATEDSPECIMENS 
         ON LOCATEDSPECIMENS.DATASET_ID = DATASETS.DATASET_ID 
         INNER JOIN dbo.ALGORITHMOUTPUTS 
         ON LOGS.ALGORITHMOUTPUT_ID = ALGORITHMOUTPUTS.ALGORITHMOUTPUT_ID
		 LEFT JOIN dbo.COMPOUNDMATERIAL 
		 ON dbo.COMPOUNDMATERIAL.startdepth = dbo.LOCATEDSPECIMENS.startdepth 
		 AND dbo.LOGS.LOG_ID = dbo.COMPOUNDMATERIAL.LOG_ID
 WHERE LOGS.logtype          IN (1,2) and LOGS.ispublic=1 and datasets.ispublic=1
  AND LOGS.algorithmoutput_id IN ( 1, 12, 32, 40, 48, 74, 88, 7, 20, 26, 82, 96)
 GROUP BY  LOCATEDSPECIMENS.LS_ID, 
      LOGS.LOG_ID,
      LOCATEDSPECIMENS.startdepth,
      LOCATEDSPECIMENS.enddepth,
      LOGS.LOGNAME,  
      LOGS.CREATEDDATE;
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[CREATETSARETRAINING]  
   @v_tsaretrainid varchar(max),
   @v_numallclasses int,
   @v_numtrainclasses int,
   @v_tsaparams varbinary(max),
   @v_minmask varbinary(max),
   @v_mixmask varbinary(max),
   @v_dbix int,
   @v_trainsel int,
   @v_illite int,
   @v_seven int,
   @v_domained int,
   @v_version int,
   @v_plus int
AS 
   
   BEGIN

      BEGIN TRY
         INSERT dbo.TSARETRAINING(
            TSARETRAINING_ID, 
            NUMALLCLASSES, 
            NUMTRAINCLASSES, 
            TSAPARAMS, 
            MINMASK, 
            MIXMASK, 
            DBIX,
            TRAINSEL,
            ILLITE,
            SEVEN,
			DOMAINED,
			VERSION,
			PLUS)
            VALUES (
               @v_tsaretrainid, 
               @v_numallclasses, 
               @v_numtrainclasses, 
               @v_tsaparams, 
               @v_minmask, 
               @v_mixmask, 
               @v_dbix,
               @v_trainsel,
               @v_illite,
               @v_seven,
			   @v_domained,
               @v_version,
			   @v_plus)
      END TRY

      BEGIN CATCH

        EXECUTE dbo.UPDATETSARETRAINING 
               @V_NUMALLCLASSES = @v_numallclasses, 
               @V_NUMTRAINCLASSES = @v_numtrainclasses, 
               @V_TSAPARAMS = @v_tsaparams, 
               @V_MINMASK = @v_minmask, 
               @V_MIXMASK = @v_mixmask, 
               @V_TSARETRAINID = @v_tsaretrainid, 
               @V_DBIX = @v_dbix,
               @V_TRAINSEL = @v_trainsel,
			   @V_ILLITE = @v_illite,
			   @V_SEVEN = @v_seven,
			   @V_DOMAINED = @v_domained,
			   @V_VERSION = @v_version,
			   @V_PLUS = @v_plus

      END CATCH

   END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[VALIDATECLASSLOGDATA] 
(
	 @v_classlogid varchar(max)
)
RETURNS int
AS
BEGIN
      DECLARE
         @algid int, 
         @totalsamps int, 
         @validsamps int

      SELECT @algid = LOGS.ALGORITHMOUTPUT_ID
      FROM dbo.LOGS
      WHERE LOGS.LOG_ID = @v_classlogid

      SELECT @totalsamps = count_big(*)
      FROM dbo.CLASSLOGDATA
      WHERE CLASSLOGDATA.LOG_ID = @v_classlogid AND CLASSLOGDATA.CLASSLOGVALUE IS NOT NULL

      IF (@algid IS NULL)
         BEGIN

            SELECT @validsamps = count_big(*)
            FROM 
               dbo.CLASSLOGDATA 
                  INNER JOIN dbo.CLASSSPECIFICCLASSIFICATIONS 
                  ON CLASSLOGDATA.CLASSLOGVALUE = CLASSSPECIFICCLASSIFICATIONS.INTINDEX
            WHERE CLASSSPECIFICCLASSIFICATIONS.LOG_ID = @v_classlogid AND CLASSLOGDATA.LOG_ID = @v_classlogid

            IF (@totalsamps = @validsamps)
               BEGIN

                  RETURN 1

               END
            ELSE 
               BEGIN

                  RETURN 0

               END

         END
      ELSE 
         BEGIN

            SELECT @validsamps = count_big(*)
            FROM 
               dbo.CLASSLOGDATA 
                  INNER JOIN dbo.CLASSIFICATIONS 
                  ON CLASSLOGDATA.CLASSLOGVALUE = CLASSIFICATIONS.INTINDEX
            WHERE CLASSIFICATIONS.ALGORITHMOUTPUT_ID = @algid AND CLASSLOGDATA.LOG_ID = @v_classlogid

            IF (@totalsamps = @validsamps)
               BEGIN

                  RETURN 1

               END
            ELSE 
               BEGIN

                  RETURN 0

               END

         END

	RETURN 0

END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--USE [NVCL]
--GO

CREATE VIEW [dbo].[GETPUBLISHEDUSERTSA] (
   DATASET_ID, 
   SAMPLENUMBER, 
   STARTVALUE, 
   ENDVALUE, 
   USERTSASMINERAL1, 
   USERTSASMINERAL2, 
   USERTSASGROUP1, 
   USERTSASGROUP2, 
   USERTSASWEIGHT1, 
   USERTSASWEIGHT2, 
   USERTSAVMINERAL1, 
   USERTSAVMINERAL2, 
   USERTSAVGROUP1, 
   USERTSAVGROUP2, 
   USERTSAVWEIGHT1, 
   USERTSAVWEIGHT2)
AS
SELECT TOP 9223372036854775807 WITH TIES 
      PUBLISHEDDATASETS.DATASET_ID, 
      DOMAINLOGDATA.SAMPLENUMBER, 
      DOMAINLOGDATA.STARTVALUE, 
      DOMAINLOGDATA.ENDVALUE, 
      coalesce(utsasmin1class.CLASSTEXT,'NULL') AS USERTSASMINERAL1, 
      coalesce(utsasmin2class.CLASSTEXT,'NULL') AS USERTSASMINERAL2, 
      coalesce(utsasgrp1class.CLASSTEXT,'NULL') AS USERTSASGROUP1, 
      coalesce(utsasgrp2class.CLASSTEXT,'NULL') AS USERTSASGROUP2, 
      coalesce(utsaswei1data.DECIMALVALUE,0) AS USERTSASWEIGHT1, 
      coalesce(utsaswei2data.DECIMALVALUE,0) AS USERTSASWEIGHT2, 
      coalesce(utsavmin1class.CLASSTEXT,'NULL') AS USERTSAVMINERAL1, 
      coalesce(utsavmin2class.CLASSTEXT,'NULL') AS USERTSAVMINERAL2, 
      coalesce(utsavgrp1class.CLASSTEXT,'NULL') AS USERTSAVGROUP1, 
      coalesce(utsavgrp2class.CLASSTEXT,'NULL') AS USERTSAVGROUP2, 
      coalesce(utsavwei1data.DECIMALVALUE,0) AS USERTSAVWEIGHT1, 
      coalesce(utsavwei2data.DECIMALVALUE,0) AS USERTSAVWEIGHT2
   FROM 
      dbo.DOMAINLOGDATA 
         INNER JOIN dbo.PUBLISHEDDATASETS 
         ON 
            PUBLISHEDDATASETS.DOMAIN_ID = DOMAINLOGDATA.LOG_ID 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsasmin1 
         ON 
            utsasmin1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsasmin1.mixnumber =0 AND 
            utsasmin1.TSARETRAINING_ID is NOT null
         INNER JOIN dbo.Algorithmoutputs AS swirminalg
         ON 
            utsasmin1.algorithmoutput_id = swirminalg.algorithmoutput_id AND
            swirminalg.algorithmoutputname = 'SWIR Mineral'
         INNER JOIN dbo.CLASSLOGDATA  AS utsasmin1data 
         ON 
            utsasmin1data.LOG_ID = utsasmin1.LOG_ID AND
            utsasmin1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsasmin1class 
         ON 
            utsasmin1class.ALGORITHMOUTPUT_ID = utsasmin1.ALGORITHMOUTPUT_ID AND
            utsasmin1data.CLASSLOGVALUE = utsasmin1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsasmin2 
         ON 
            utsasmin2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsasmin2.mixnumber =1 AND 
            utsasmin2.TSARETRAINING_ID is NOT null AND
            utsasmin2.algorithmoutput_id = swirminalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS utsasmin2data 
         ON 
            utsasmin2data.LOG_ID = utsasmin2.LOG_ID AND
            utsasmin2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsasmin2class 
         ON 
            utsasmin2class.ALGORITHMOUTPUT_ID = utsasmin2.ALGORITHMOUTPUT_ID AND
            utsasmin2data.CLASSLOGVALUE = utsasmin2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsasgrp1 
         ON 
            utsasgrp1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsasgrp1.MIXNUMBER = 0 AND 
            utsasgrp1.TSARETRAINING_ID is NOT null
         INNER JOIN dbo.Algorithmoutputs AS swirgrpalg   
         ON
             utsasgrp1.algorithmoutput_id = swirgrpalg.algorithmoutput_id AND
             swirgrpalg.algorithmoutputname = 'SWIR Group'
         INNER JOIN dbo.CLASSLOGDATA  AS utsasgrp1data 
         ON 
            utsasgrp1data.LOG_ID = utsasgrp1.LOG_ID AND
            utsasgrp1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsasgrp1class 
         ON 
            utsasgrp1class.ALGORITHMOUTPUT_ID = utsasgrp1.ALGORITHMOUTPUT_ID AND
            utsasgrp1data.CLASSLOGVALUE = utsasgrp1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsasgrp2 
         ON 
            utsasgrp2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsasgrp2.MIXNUMBER = 1 AND 
            utsasgrp2.TSARETRAINING_ID is NOT null AND
            utsasgrp2.algorithmoutput_id = swirgrpalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS utsasgrp2data 
         ON 
            utsasgrp2data.LOG_ID = utsasgrp2.LOG_ID AND
            utsasgrp2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsasgrp2class 
         ON 
            utsasgrp2class.ALGORITHMOUTPUT_ID = utsasgrp2.ALGORITHMOUTPUT_ID AND
            utsasgrp2data.CLASSLOGVALUE = utsasgrp2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsaswei1 
         ON 
            utsaswei1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsaswei1.MIXNUMBER = 0 AND 
            utsaswei1.TSARETRAINING_ID is NOT null
         INNER JOIN dbo.Algorithmoutputs swirweialg
         ON
            utsaswei1.algorithmoutput_id = swirweialg.algorithmoutput_id AND 
            swirweialg.algorithmoutputname =  'SWIR Weight'
         INNER JOIN dbo.DECIMALLOGDATA  AS utsaswei1data 
         ON 
            utsaswei1data.LOG_ID = utsaswei1.LOG_ID AND
            utsaswei1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsaswei2 
         ON 
            utsaswei2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsaswei2.mixnumber =1 AND 
            utsaswei2.TSARETRAINING_ID is NOT null AND
            utsaswei2.algorithmoutput_id = swirweialg.algorithmoutput_id
         INNER JOIN dbo.DECIMALLOGDATA  AS utsaswei2data 
         ON 
            utsaswei2data.LOG_ID = utsaswei2.LOG_ID AND
            utsaswei2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsavmin1 
         ON 
            utsavmin1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsavmin1.MIXNUMBER = 0 AND 
            utsavmin1.TSARETRAINING_ID is NOT null
         INNER JOIN dbo.Algorithmoutputs vnirminalg
         ON
            utsavmin1.algorithmoutput_id = vnirminalg.algorithmoutput_id AND
            vnirminalg.algorithmoutputname = 'VNIR Mineral'
         INNER JOIN dbo.CLASSLOGDATA  AS utsavmin1data 
         ON 
            utsavmin1data.LOG_ID = utsavmin1.LOG_ID AND
            utsavmin1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsavmin1class 
         ON 
           utsavmin1class.ALGORITHMOUTPUT_ID = utsavmin1.ALGORITHMOUTPUT_ID AND
           utsavmin1data.CLASSLOGVALUE = utsavmin1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsavmin2 
         ON 
            utsavmin2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsavmin2.MIXNUMBER = 1 AND 
            utsavmin2.TSARETRAINING_ID is NOT null AND
            utsavmin2.algorithmoutput_id = vnirminalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS utsavmin2data 
         ON 
            utsavmin2data.LOG_ID = utsavmin2.LOG_ID AND
            utsavmin2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsavmin2class 
         ON 
            utsavmin2class.ALGORITHMOUTPUT_ID = utsavmin2.ALGORITHMOUTPUT_ID AND
            utsavmin2data.CLASSLOGVALUE = utsavmin2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsavgrp1 
         ON 
            utsavgrp1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsavgrp1.MIXNUMBER = 0 AND 
            utsavgrp1.TSARETRAINING_ID is NOT null
         INNER JOIN dbo.Algorithmoutputs vnirgrpalg
         ON
            utsavgrp1.algorithmoutput_id = vnirgrpalg.algorithmoutput_id AND
            vnirgrpalg.algorithmoutputname = 'VNIR Group'
         INNER JOIN dbo.CLASSLOGDATA  AS utsavgrp1data 
         ON 
            utsavgrp1data.LOG_ID = utsavgrp1.LOG_ID AND
            utsavgrp1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsavgrp1class 
         ON 
            utsavgrp1class.ALGORITHMOUTPUT_ID = utsavgrp1.ALGORITHMOUTPUT_ID AND
            utsavgrp1data.CLASSLOGVALUE = utsavgrp1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsavgrp2 
         ON 
            utsavgrp2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsavgrp2.MIXNUMBER = 1 AND 
            utsavgrp2.TSARETRAINING_ID is NOT null AND
            utsavgrp2.algorithmoutput_id = vnirgrpalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS utsavgrp2data 
         ON 
            utsavgrp2data.LOG_ID = utsavgrp2.LOG_ID AND
            utsavgrp2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS utsavgrp2class 
         ON 
            utsavgrp2class.ALGORITHMOUTPUT_ID = utsavgrp2.ALGORITHMOUTPUT_ID AND
            utsavgrp2data.CLASSLOGVALUE = utsavgrp2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsavwei1 
         ON 
            utsavwei1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsavwei1.MIXNUMBER = 0 AND 
            utsavwei1.TSARETRAINING_ID is NOT null
         INNER JOIN dbo.Algorithmoutputs vnirweialg
         ON
            utsavwei1.algorithmoutput_id = vnirweialg.algorithmoutput_id AND
            vnirweialg.algorithmoutputname =  'VNIR Weight'   
         INNER JOIN dbo.DECIMALLOGDATA  AS utsavwei1data 
         ON 
            utsavwei1data.LOG_ID = utsavwei1.LOG_ID AND
            utsavwei1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         INNER JOIN dbo.PUBLISHEDLOGS  AS utsavwei2 
         ON 
            utsavwei2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            utsavwei2.MIXNUMBER = 1 AND 
            utsavwei2.TSARETRAINING_ID is NOT null AND
            utsavwei2.algorithmoutput_id = vnirweialg.algorithmoutput_id
         INNER JOIN dbo.DECIMALLOGDATA  AS utsavwei2data 
         ON 
         utsavwei2data.LOG_ID = utsavwei2.LOG_ID AND
         utsavwei2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER
   ORDER BY utsavmin1data.SAMPLENUMBER
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GETPUBLISHEDSYSTEMTSA] (
   DATASET_ID, 
   SAMPLENUMBER, 
   STARTVALUE, 
   ENDVALUE, 
   SYSTEMTSASMINERAL1, 
   SYSTEMTSASMINERAL2, 
   SYSTEMTSASGROUP1, 
   SYSTEMTSASGROUP2, 
   SYSTEMTSASWEIGHT1, 
   SYSTEMTSASWEIGHT2, 
   SYSTEMTSAVMINERAL1, 
   SYSTEMTSAVMINERAL2, 
   SYSTEMTSAVGROUP1, 
   SYSTEMTSAVGROUP2, 
   SYSTEMTSAVWEIGHT1, 
   SYSTEMTSAVWEIGHT2)
AS
SELECT TOP 9223372036854775807 WITH TIES 
      PUBLISHEDDATASETS.DATASET_ID, 
      DOMAINLOGDATA.SAMPLENUMBER, 
      DOMAINLOGDATA.STARTVALUE, 
      DOMAINLOGDATA.ENDVALUE, 
      coalesce(stsasmin1class.CLASSTEXT,'NULL') AS SYSTEMTSASMINERAL1, 
      coalesce(stsasmin2class.CLASSTEXT,'NULL') AS SYSTEMTSASMINERAL2, 
      coalesce(stsasgrp1class.CLASSTEXT,'NULL') AS SYSTEMTSASGROUP1, 
      coalesce(stsasgrp2class.CLASSTEXT,'NULL') AS SYSTEMTSASGROUP2, 
      coalesce(stsaswei1data.DECIMALVALUE,0) AS SYSTEMTSASWEIGHT1, 
      coalesce(stsaswei2data.DECIMALVALUE,0) AS SYSTEMTSASWEIGHT2, 
      coalesce(stsavmin1class.CLASSTEXT,'NULL') AS SYSTEMTSAVMINERAL1, 
      coalesce(stsavmin2class.CLASSTEXT,'NULL') AS SYSTEMTSAVMINERAL2, 
      coalesce(stsavgrp1class.CLASSTEXT,'NULL') AS SYSTEMTSAVGROUP1, 
      coalesce(stsavgrp2class.CLASSTEXT,'NULL') AS SYSTEMTSAVGROUP2, 
      coalesce(stsavwei1data.DECIMALVALUE,0) AS SYSTEMTSAVWEIGHT1, 
      coalesce(stsavwei2data.DECIMALVALUE,0) AS SYSTEMTSAVWEIGHT2
   FROM 
      dbo.DOMAINLOGDATA 
         INNER JOIN dbo.PUBLISHEDDATASETS 
         ON 
            PUBLISHEDDATASETS.DOMAIN_ID = DOMAINLOGDATA.LOG_ID 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsasmin1 
         ON 
            stsasmin1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsasmin1.mixnumber =0 AND 
            stsasmin1.TSARETRAINING_ID is null
         INNER JOIN dbo.Algorithmoutputs AS swirminalg
         ON 
            stsasmin1.algorithmoutput_id = swirminalg.algorithmoutput_id AND
            swirminalg.algorithmoutputname = 'SWIR Mineral'
         INNER JOIN dbo.CLASSLOGDATA  AS stsasmin1data 
         ON 
            stsasmin1data.LOG_ID = stsasmin1.LOG_ID AND
            stsasmin1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsasmin1class 
         ON 
            stsasmin1class.ALGORITHMOUTPUT_ID = stsasmin1.ALGORITHMOUTPUT_ID AND
            stsasmin1data.CLASSLOGVALUE = stsasmin1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsasmin2 
         ON 
            stsasmin2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsasmin2.mixnumber =1 AND 
            stsasmin2.TSARETRAINING_ID is null AND
            stsasmin2.algorithmoutput_id = swirminalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS stsasmin2data 
         ON 
            stsasmin2data.LOG_ID = stsasmin2.LOG_ID AND
            stsasmin2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsasmin2class 
         ON 
            stsasmin2class.ALGORITHMOUTPUT_ID = stsasmin2.ALGORITHMOUTPUT_ID AND
            stsasmin2data.CLASSLOGVALUE = stsasmin2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsasgrp1 
         ON 
            stsasgrp1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsasgrp1.MIXNUMBER = 0 AND 
            stsasgrp1.TSARETRAINING_ID is null
         INNER JOIN dbo.Algorithmoutputs AS swirgrpalg   
         ON
             stsasgrp1.algorithmoutput_id = swirgrpalg.algorithmoutput_id AND
             swirgrpalg.algorithmoutputname = 'SWIR Group'
         INNER JOIN dbo.CLASSLOGDATA  AS stsasgrp1data 
         ON 
            stsasgrp1data.LOG_ID = stsasgrp1.LOG_ID AND
            stsasgrp1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsasgrp1class 
         ON 
            stsasgrp1class.ALGORITHMOUTPUT_ID = stsasgrp1.ALGORITHMOUTPUT_ID AND
            stsasgrp1data.CLASSLOGVALUE = stsasgrp1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsasgrp2 
         ON 
            stsasgrp2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsasgrp2.MIXNUMBER = 1 AND 
            stsasgrp2.TSARETRAINING_ID is null AND
            stsasgrp2.algorithmoutput_id = swirgrpalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS stsasgrp2data 
         ON 
            stsasgrp2data.LOG_ID = stsasgrp2.LOG_ID AND
            stsasgrp2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsasgrp2class 
         ON 
            stsasgrp2class.ALGORITHMOUTPUT_ID = stsasgrp2.ALGORITHMOUTPUT_ID AND
            stsasgrp2data.CLASSLOGVALUE = stsasgrp2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsaswei1 
         ON 
            stsaswei1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsaswei1.MIXNUMBER = 0 AND 
            stsaswei1.TSARETRAINING_ID is null
         INNER JOIN dbo.Algorithmoutputs swirweialg
         ON
            stsaswei1.algorithmoutput_id = swirweialg.algorithmoutput_id AND 
            swirweialg.algorithmoutputname =  'SWIR Weight'
         INNER JOIN dbo.DECIMALLOGDATA  AS stsaswei1data 
         ON 
            stsaswei1data.LOG_ID = stsaswei1.LOG_ID AND
            stsaswei1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsaswei2 
         ON 
            stsaswei2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsaswei2.mixnumber =1 AND 
            stsaswei2.TSARETRAINING_ID is null AND
            stsaswei2.algorithmoutput_id = swirweialg.algorithmoutput_id
         INNER JOIN dbo.DECIMALLOGDATA  AS stsaswei2data 
         ON 
            stsaswei2data.LOG_ID = stsaswei2.LOG_ID AND
            stsaswei2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsavmin1 
         ON 
            stsavmin1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsavmin1.MIXNUMBER = 0 AND 
            stsavmin1.TSARETRAINING_ID is null
         INNER JOIN dbo.Algorithmoutputs vnirminalg
         ON
            stsavmin1.algorithmoutput_id = vnirminalg.algorithmoutput_id AND
            vnirminalg.algorithmoutputname = 'VNIR Mineral'
         INNER JOIN dbo.CLASSLOGDATA  AS stsavmin1data 
         ON 
            stsavmin1data.LOG_ID = stsavmin1.LOG_ID AND
            stsavmin1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsavmin1class 
         ON 
           stsavmin1class.ALGORITHMOUTPUT_ID = stsavmin1.ALGORITHMOUTPUT_ID AND
           stsavmin1data.CLASSLOGVALUE = stsavmin1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsavmin2 
         ON 
            stsavmin2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsavmin2.MIXNUMBER = 1 AND 
            stsavmin2.TSARETRAINING_ID is null AND
            stsavmin2.algorithmoutput_id = vnirminalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS stsavmin2data 
         ON 
            stsavmin2data.LOG_ID = stsavmin2.LOG_ID AND
            stsavmin2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsavmin2class 
         ON 
            stsavmin2class.ALGORITHMOUTPUT_ID = stsavmin2.ALGORITHMOUTPUT_ID AND
            stsavmin2data.CLASSLOGVALUE = stsavmin2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsavgrp1 
         ON 
            stsavgrp1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsavgrp1.MIXNUMBER = 0 AND 
            stsavgrp1.TSARETRAINING_ID is null
         INNER JOIN dbo.Algorithmoutputs vnirgrpalg
         ON
            stsavgrp1.algorithmoutput_id = vnirgrpalg.algorithmoutput_id AND
            vnirgrpalg.algorithmoutputname = 'VNIR Group'
         INNER JOIN dbo.CLASSLOGDATA  AS stsavgrp1data 
         ON 
            stsavgrp1data.LOG_ID = stsavgrp1.LOG_ID AND
            stsavgrp1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsavgrp1class 
         ON 
            stsavgrp1class.ALGORITHMOUTPUT_ID = stsavgrp1.ALGORITHMOUTPUT_ID AND
            stsavgrp1data.CLASSLOGVALUE = stsavgrp1class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsavgrp2 
         ON 
            stsavgrp2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsavgrp2.MIXNUMBER = 1 AND 
            stsavgrp2.TSARETRAINING_ID is null AND
            stsavgrp2.algorithmoutput_id = vnirgrpalg.algorithmoutput_id
         INNER JOIN dbo.CLASSLOGDATA  AS stsavgrp2data 
         ON 
            stsavgrp2data.LOG_ID = stsavgrp2.LOG_ID AND
            stsavgrp2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         LEFT OUTER JOIN dbo.CLASSIFICATIONS  AS stsavgrp2class 
         ON 
            stsavgrp2class.ALGORITHMOUTPUT_ID = stsavgrp2.ALGORITHMOUTPUT_ID AND
            stsavgrp2data.CLASSLOGVALUE = stsavgrp2class.INTINDEX 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsavwei1 
         ON 
            stsavwei1.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsavwei1.MIXNUMBER = 0 AND 
            stsavwei1.TSARETRAINING_ID is null
         INNER JOIN dbo.Algorithmoutputs vnirweialg
         ON
            stsavwei1.algorithmoutput_id = vnirweialg.algorithmoutput_id AND
            vnirweialg.algorithmoutputname =  'VNIR Weight'   
         INNER JOIN dbo.DECIMALLOGDATA  AS stsavwei1data 
         ON 
            stsavwei1data.LOG_ID = stsavwei1.LOG_ID AND
            stsavwei1data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER 
         INNER JOIN dbo.PUBLISHEDLOGS  AS stsavwei2 
         ON 
            stsavwei2.DATASET_ID = PUBLISHEDDATASETS.DATASET_ID AND 
            stsavwei2.MIXNUMBER = 1 AND 
            stsavwei2.TSARETRAINING_ID is null AND
            stsavwei2.algorithmoutput_id = vnirweialg.algorithmoutput_id
         INNER JOIN dbo.DECIMALLOGDATA  AS stsavwei2data 
         ON 
         stsavwei2data.LOG_ID = stsavwei2.LOG_ID AND
         stsavwei2data.SAMPLENUMBER = DOMAINLOGDATA.SAMPLENUMBER
   ORDER BY stsavmin1data.SAMPLENUMBER
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GETDOWNSAMPLEDLOGVALUES]  
   @v_logid varchar(max),
   @v_startdepth float(53),
   @v_enddepth float(53),
   @v_interval float(53) = 1,
   @v_threshold float(53) =5
AS 
   
   BEGIN

      DECLARE
         @logtype int, 
         @stdalg int,
		 @masklogid varchar(200)
		 
      SELECT @logtype = LOGS.LOGTYPE, @stdalg = LOGS.ALGORITHMOUTPUT_ID, @masklogid =LOGS.MASKLOG_ID
      FROM dbo.LOGS
      WHERE LOGS.LOG_ID = @v_logid

      IF @logtype = 1
         IF (@stdalg IS NULL)
		    IF (@masklogid IS NULL)
				BEGIN

				SELECT floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval + @v_interval / 2 AS roundeddepth, count_big(*) AS classcount, CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT, CLASSSPECIFICCLASSIFICATIONS.COLOUR
						 FROM 
							dbo.DOMAINLOGDATA 
							   LEFT OUTER JOIN dbo.CLASSLOGDATA 
							   ON DOMAINLOGDATA.SAMPLENUMBER = CLASSLOGDATA.SAMPLENUMBER 
							   INNER JOIN dbo.CLASSSPECIFICCLASSIFICATIONS 
							   ON CLASSLOGDATA.CLASSLOGVALUE = CLASSSPECIFICCLASSIFICATIONS.INTINDEX
						 WHERE 
							CLASSLOGDATA.LOG_ID = @v_logid AND 
							CLASSSPECIFICCLASSIFICATIONS.LOG_ID = @v_logid AND 
							DOMAINLOGDATA.LOG_ID = 
							(
							   SELECT LOGS.DOMAINLOG_ID
							   FROM dbo.LOGS
							   WHERE LOGS.LOG_ID = @v_logid
							) AND 
							DOMAINLOGDATA.STARTVALUE >= @v_startdepth AND 
							DOMAINLOGDATA.ENDVALUE < @v_enddepth
						 GROUP BY CLASSLOGDATA.CLASSLOGVALUE, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT, CLASSSPECIFICCLASSIFICATIONS.COLOUR
						 HAVING count_big(*) > @v_threshold*@v_interval
						 ORDER BY CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, classcount DESC

				END
			ELSE
				BEGIN
				SELECT floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval + @v_interval / 2 AS roundeddepth, count_big(*) AS classcount, CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT, CLASSSPECIFICCLASSIFICATIONS.COLOUR
						 FROM 
							dbo.DOMAINLOGDATA 
							   LEFT OUTER JOIN dbo.CLASSLOGDATA 
							   ON DOMAINLOGDATA.SAMPLENUMBER = CLASSLOGDATA.SAMPLENUMBER 
							   INNER JOIN dbo.MASKLOGDATA
							   ON MASKLOGDATA.SAMPLENUMBER=classlogdata.SAMPLENUMBER
							   INNER JOIN dbo.CLASSSPECIFICCLASSIFICATIONS 
							   ON CLASSLOGDATA.CLASSLOGVALUE = CLASSSPECIFICCLASSIFICATIONS.INTINDEX
						 WHERE 
							MASKLOGDATA.LOG_ID=@masklogid AND MASKLOGDATA.MASKVALUE=1 AND
							CLASSLOGDATA.LOG_ID = @v_logid AND 
							CLASSSPECIFICCLASSIFICATIONS.LOG_ID = @v_logid AND 
							DOMAINLOGDATA.LOG_ID = 
							(
							   SELECT LOGS.DOMAINLOG_ID
							   FROM dbo.LOGS
							   WHERE LOGS.LOG_ID = @v_logid
							) AND 
							DOMAINLOGDATA.STARTVALUE >= @v_startdepth AND 
							DOMAINLOGDATA.ENDVALUE < @v_enddepth
						 GROUP BY CLASSLOGDATA.CLASSLOGVALUE, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT, CLASSSPECIFICCLASSIFICATIONS.COLOUR
						 HAVING count_big(*) > @v_threshold*@v_interval
						 ORDER BY CLASSSPECIFICCLASSIFICATIONS.CLASSTEXT, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, classcount DESC
				END
         ELSE 
		    IF (@masklogid IS NULL)
               BEGIN
               SELECT floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval + @v_interval / 2 AS roundeddepth, count_big(*) AS classcount, CLASSIFICATIONS.CLASSTEXT, CLASSIFICATIONS.COLOUR
                     FROM 
                        dbo.DOMAINLOGDATA 
                           LEFT OUTER JOIN dbo.CLASSLOGDATA 
                           ON DOMAINLOGDATA.SAMPLENUMBER = CLASSLOGDATA.SAMPLENUMBER 
                           INNER JOIN dbo.CLASSIFICATIONS 
                           ON CLASSLOGDATA.CLASSLOGVALUE = CLASSIFICATIONS.INTINDEX
                     WHERE 
                        CLASSLOGDATA.LOG_ID = @v_logid AND 
                        CLASSIFICATIONS.ALGORITHMOUTPUT_ID = @stdalg AND 
                        DOMAINLOGDATA.LOG_ID = 
                        (
                           SELECT LOGS.DOMAINLOG_ID
                           FROM dbo.LOGS
                           WHERE LOGS.LOG_ID = @v_logid
                        ) AND 
                        DOMAINLOGDATA.STARTVALUE >= @v_startdepth AND 
                        DOMAINLOGDATA.ENDVALUE < @v_enddepth
                     GROUP BY CLASSLOGDATA.CLASSLOGVALUE, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, CLASSIFICATIONS.CLASSTEXT, CLASSIFICATIONS.COLOUR
                     HAVING count_big(*) > @v_threshold*@v_interval
                     ORDER BY CLASSIFICATIONS.CLASSTEXT, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, classcount DESC
            END
		    ELSE
			   BEGIN
               SELECT floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval + @v_interval / 2 AS roundeddepth, count_big(*) AS classcount, CLASSIFICATIONS.CLASSTEXT, CLASSIFICATIONS.COLOUR
                     FROM 
                        dbo.DOMAINLOGDATA 
                           LEFT OUTER JOIN dbo.CLASSLOGDATA 
                           ON DOMAINLOGDATA.SAMPLENUMBER = CLASSLOGDATA.SAMPLENUMBER 
						   INNER JOIN dbo.MASKLOGDATA
						   ON MASKLOGDATA.SAMPLENUMBER=classlogdata.SAMPLENUMBER
                           INNER JOIN dbo.CLASSIFICATIONS 
                           ON CLASSLOGDATA.CLASSLOGVALUE = CLASSIFICATIONS.INTINDEX
                     WHERE 
                        MASKLOGDATA.LOG_ID=@masklogid AND MASKLOGDATA.MASKVALUE=1 AND
						CLASSLOGDATA.LOG_ID = @v_logid AND 
                        CLASSIFICATIONS.ALGORITHMOUTPUT_ID = @stdalg AND 
                        DOMAINLOGDATA.LOG_ID = 
                        (
                           SELECT LOGS.DOMAINLOG_ID
                           FROM dbo.LOGS
                           WHERE LOGS.LOG_ID = @v_logid
                        ) AND 
                        DOMAINLOGDATA.STARTVALUE >= @v_startdepth AND 
                        DOMAINLOGDATA.ENDVALUE < @v_enddepth
                     GROUP BY CLASSLOGDATA.CLASSLOGVALUE, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, CLASSIFICATIONS.CLASSTEXT, CLASSIFICATIONS.COLOUR
                     HAVING count_big(*) > @v_threshold*@v_interval
                     ORDER BY CLASSIFICATIONS.CLASSTEXT, floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval, classcount DESC
            END
      ELSE 
         IF @logtype = 2
			 IF (@masklogid IS NULL)
				BEGIN
				   SELECT floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval + @v_interval / 2 AS roundeddepth, avg(DECIMALLOGDATA.DECIMALVALUE) AS averagevalue
						 FROM 
							dbo.DOMAINLOGDATA 
							   LEFT JOIN dbo.DECIMALLOGDATA 
							   ON DOMAINLOGDATA.SAMPLENUMBER = DECIMALLOGDATA.SAMPLENUMBER
						 WHERE 
							DECIMALLOGDATA.LOG_ID = @v_logid AND 
							DOMAINLOGDATA.LOG_ID = 
							(
							   SELECT LOGS.DOMAINLOG_ID
							   FROM dbo.LOGS
							   WHERE LOGS.LOG_ID = @v_logid
							) AND 
							DOMAINLOGDATA.STARTVALUE >= @v_startdepth AND 
							DOMAINLOGDATA.ENDVALUE < @v_enddepth
						 GROUP BY floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval
						 ORDER BY floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval

				END
			ELSE
				BEGIN
				   SELECT floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval + @v_interval / 2 AS roundeddepth, avg(DECIMALLOGDATA.DECIMALVALUE) AS averagevalue
						 FROM 
							dbo.DOMAINLOGDATA 
							   LEFT JOIN dbo.DECIMALLOGDATA 
							   ON DOMAINLOGDATA.SAMPLENUMBER = DECIMALLOGDATA.SAMPLENUMBER
							   INNER JOIN dbo.MASKLOGDATA
							   ON MASKLOGDATA.SAMPLENUMBER=DECIMALLOGDATA.SAMPLENUMBER
						 WHERE 
							MASKLOGDATA.LOG_ID=@masklogid AND MASKLOGDATA.MASKVALUE=1 AND
							DECIMALLOGDATA.LOG_ID = @v_logid AND 
							DOMAINLOGDATA.LOG_ID = 
							(
							   SELECT LOGS.DOMAINLOG_ID
							   FROM dbo.LOGS
							   WHERE LOGS.LOG_ID = @v_logid
							) AND 
							DOMAINLOGDATA.STARTVALUE >= @v_startdepth AND 
							DOMAINLOGDATA.ENDVALUE < @v_enddepth
						 GROUP BY floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval
						 ORDER BY floor(DOMAINLOGDATA.STARTVALUE / @v_interval) * @v_interval

				END
         ELSE 
            BEGIN

               DECLARE
                  @db_raise_application_error_message nvarchar(4000)

               SET @db_raise_application_error_message = N'you cannot downsample a log of that type'

               RAISERROR(59998, 16, 1, @db_raise_application_error_message)

            END

   END
GO
ALTER TABLE [dbo].[DECIMALLOGS]  WITH CHECK ADD  CONSTRAINT [DECIMALLOGS_ISRBGCOLOUR_CHK] CHECK  (([RESULTISRGBCOLOUR]=(1) OR [RESULTISRGBCOLOUR]=(0)))
GO
ALTER TABLE [dbo].[DECIMALLOGS] CHECK CONSTRAINT [DECIMALLOGS_ISRBGCOLOUR_CHK]
GO
ALTER TABLE [dbo].[MASKLOGDATA]  WITH CHECK ADD  CONSTRAINT [MASKLOGDATA_CHK] CHECK  (([MASKVALUE]=(1) OR [MASKVALUE]=(0)))
GO
ALTER TABLE [dbo].[MASKLOGDATA] CHECK CONSTRAINT [MASKLOGDATA_CHK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_ISPUBLIC_CHK] CHECK  (([ISPUBLIC]=(1) OR [ISPUBLIC]=(0)))
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_ISPUBLIC_CHK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_ISPUBLIC_CHK] CHECK  (([ISPUBLIC]=(1) OR [ISPUBLIC]=(0)))
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_ISPUBLIC_CHK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_ISREFLIBRARY_CHK] CHECK  (([ISREFERENCELIBRARY]=(1) OR [ISREFERENCELIBRARY]=(0)))
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_ISREFLIBRARY_CHK]
GO
ALTER TABLE [dbo].[ALGORITHMS]  WITH CHECK ADD  CONSTRAINT [ALGORITHMS_ISPUBLIC_CHK] CHECK  (([ISPUBLIC]=(1) OR [ISPUBLIC]=(0)))
GO
ALTER TABLE [dbo].[ALGORITHMS] CHECK CONSTRAINT [ALGORITHMS_ISPUBLIC_CHK]
GO
ALTER TABLE [dbo].[DECIMALLOGS]  WITH CHECK ADD  CONSTRAINT [DECIMALLOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DECIMALLOGS] CHECK CONSTRAINT [DECIMALLOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[DECIMALLOGS]  WITH CHECK ADD  CONSTRAINT [DECIMALSCALARGROUP_FK] FOREIGN KEY([SCALARGROUP_ID])
REFERENCES [dbo].[SCALARGROUPS] ([SCALARGROUP_ID])
GO
ALTER TABLE [dbo].[DECIMALLOGS] CHECK CONSTRAINT [DECIMALSCALARGROUP_FK]
GO
ALTER TABLE [dbo].[DECIMALLOGDATA]  WITH CHECK ADD  CONSTRAINT [DECIMALLOGDATA_DECIMALLOG_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[DECIMALLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DECIMALLOGDATA] CHECK CONSTRAINT [DECIMALLOGDATA_DECIMALLOG_FK]
GO
ALTER TABLE [dbo].[MASKLOGS]  WITH CHECK ADD  CONSTRAINT [MASKLOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[MASKLOGS] CHECK CONSTRAINT [MASKLOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[MASKLOGS]  WITH CHECK ADD  CONSTRAINT [MASKSCALARGROUP_FK] FOREIGN KEY([SCALARGROUP_ID])
REFERENCES [dbo].[SCALARGROUPS] ([SCALARGROUP_ID])
GO
ALTER TABLE [dbo].[MASKLOGS] CHECK CONSTRAINT [MASKSCALARGROUP_FK]
GO
ALTER TABLE [dbo].[MASKLOGDATA]  WITH CHECK ADD  CONSTRAINT [MASKLOGDATA_MASKLOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[MASKLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[MASKLOGDATA] CHECK CONSTRAINT [MASKLOGDATA_MASKLOGS_FK]
GO
ALTER TABLE [dbo].[PROFLOGS]  WITH CHECK ADD  CONSTRAINT [PROFLOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PROFLOGS] CHECK CONSTRAINT [PROFLOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[PROFLOGDATA]  WITH CHECK ADD  CONSTRAINT [PROFLOGDATA_PROFLOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[PROFLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PROFLOGDATA] CHECK CONSTRAINT [PROFLOGDATA_PROFLOGS_FK]
GO
ALTER TABLE [dbo].[SPECTRALLOGS]  WITH CHECK ADD  CONSTRAINT [SPECTRALLOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SPECTRALLOGS] CHECK CONSTRAINT [SPECTRALLOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[SPECTRALLOGDATA]  WITH CHECK ADD  CONSTRAINT [SPECTRALLOGDATA_SPECTRALL_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[SPECTRALLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SPECTRALLOGDATA] CHECK CONSTRAINT [SPECTRALLOGDATA_SPECTRALL_FK]
GO
ALTER TABLE [dbo].[SCALARSETS]  WITH CHECK ADD  CONSTRAINT [SCALARSET_DATASETS_FK] FOREIGN KEY([DATASET_ID])
REFERENCES [dbo].[DATASETS] ([DATASET_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[SCALARSETS] CHECK CONSTRAINT [SCALARSET_DATASETS_FK]
GO
ALTER TABLE [dbo].[IMAGELOGS]  WITH CHECK ADD  CONSTRAINT [IMAGELOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[IMAGELOGS] CHECK CONSTRAINT [IMAGELOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[IMAGELOGDATA]  WITH CHECK ADD  CONSTRAINT [IMAGELOGDATA_IMAGELOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[IMAGELOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[IMAGELOGDATA] CHECK CONSTRAINT [IMAGELOGDATA_IMAGELOGS_FK]
GO
ALTER TABLE [dbo].[ALGORITHMOUTPUTS]  WITH CHECK ADD  CONSTRAINT [ALGORITHMOUTPUTS_ALGORITH_FK1] FOREIGN KEY([ALGORITHM_ID])
REFERENCES [dbo].[ALGORITHMS] ([ALGORITHM_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ALGORITHMOUTPUTS] CHECK CONSTRAINT [ALGORITHMOUTPUTS_ALGORITH_FK1]
GO
ALTER TABLE [dbo].[ALGORITHMOUTPUTS]  WITH CHECK ADD  CONSTRAINT [ALGORITHMS_LOGTYPES_FK] FOREIGN KEY([OUTPUTLOGTYPE])
REFERENCES [dbo].[LOGTYPES] ([LOGTYPE_ID])
GO
ALTER TABLE [dbo].[ALGORITHMOUTPUTS] CHECK CONSTRAINT [ALGORITHMS_LOGTYPES_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [FK_LOGS_PLSDATA] FOREIGN KEY([PLS_ID])
REFERENCES [dbo].[PLSDATA] ([PLS_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [FK_LOGS_PLSDATA]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_ALGORITHMOUTPUTS_FK] FOREIGN KEY([ALGORITHMOUTPUT_ID])
REFERENCES [dbo].[ALGORITHMOUTPUTS] ([ALGORITHMOUTPUT_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_ALGORITHMOUTPUTS_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_AUX_SPECTRALLOGS_FK] FOREIGN KEY([AUXSPECTRALLAYER_ID])
REFERENCES [dbo].[SPECTRALLOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_AUX_SPECTRALLOGS_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_DATASETS_FK] FOREIGN KEY([DATASET_ID])
REFERENCES [dbo].[DATASETS] ([DATASET_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_DATASETS_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_DATASETSTATS_FK] FOREIGN KEY([REFSTATS_ID])
REFERENCES [dbo].[DATASETSTATS] ([DATASETSTATS_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_DATASETSTATS_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_DOMAINLOGS_FK] FOREIGN KEY([DOMAINLOG_ID])
REFERENCES [dbo].[DOMAINLOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_DOMAINLOGS_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_LAYER_SPECTRALLOGS_FK] FOREIGN KEY([LOCALSPECTRALLAYER_ID])
REFERENCES [dbo].[SPECTRALLOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_LAYER_SPECTRALLOGS_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_LOGTYPES_FK] FOREIGN KEY([LOGTYPE])
REFERENCES [dbo].[LOGTYPES] ([LOGTYPE_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_LOGTYPES_FK]
GO
ALTER TABLE [dbo].[LOGS]  WITH CHECK ADD  CONSTRAINT [LOGS_TSARETRAINING_FK] FOREIGN KEY([TSARETRAINING_ID])
REFERENCES [dbo].[TSARETRAINING] ([TSARETRAINING_ID])
GO
ALTER TABLE [dbo].[LOGS] CHECK CONSTRAINT [LOGS_TSARETRAINING_FK]
GO
ALTER TABLE [dbo].[CALIBRATIONLOGDATA]  WITH CHECK ADD  CONSTRAINT [CALIBLOGDATA_CALIBLOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[CALIBRATIONLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CALIBRATIONLOGDATA] CHECK CONSTRAINT [CALIBLOGDATA_CALIBLOGS_FK]
GO
ALTER TABLE [dbo].[CLASSLOGDATA]  WITH CHECK ADD  CONSTRAINT [CLASSLOGDATA_CLASSLOG_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[CLASSLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CLASSLOGDATA] CHECK CONSTRAINT [CLASSLOGDATA_CLASSLOG_FK]
GO
ALTER TABLE [dbo].[DOMAINLOGDATA]  WITH CHECK ADD  CONSTRAINT [DOMAINLOGDATA_DOMAINLOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[DOMAINLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DOMAINLOGDATA] CHECK CONSTRAINT [DOMAINLOGDATA_DOMAINLOGS_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [CUSTCALCDATASETS_DATASETS_FK1] FOREIGN KEY([CUSTCALCDATASET_ID])
REFERENCES [dbo].[DATASETS] ([DATASET_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [CUSTCALCDATASETS_DATASETS_FK1]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [CUSTOMDATASET_DATASETS_FK1] FOREIGN KEY([CUSTOMDATASET_ID])
REFERENCES [dbo].[DATASETS] ([DATASET_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [CUSTOMDATASET_DATASETS_FK1]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_DOMAINLOGS_FK] FOREIGN KEY([DOMAIN_ID])
REFERENCES [dbo].[DOMAINLOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_DOMAINLOGS_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_IMAGELOGS_FK] FOREIGN KEY([IMAGELOG_ID])
REFERENCES [dbo].[IMAGELOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_IMAGELOGS_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_MACHINES_FK] FOREIGN KEY([PRIMARYLOGGER_ID])
REFERENCES [dbo].[MACHINES] ([MACHINE_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_MACHINES_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_PROFLOGS_FK] FOREIGN KEY([PROFLOG_ID])
REFERENCES [dbo].[PROFLOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_PROFLOGS_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_SECTION_LOGS_FK] FOREIGN KEY([SECTIONLOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_SECTION_LOGS_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_SPECTRALLOGS_FK] FOREIGN KEY([SPECLOG_ID])
REFERENCES [dbo].[SPECTRALLOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_SPECTRALLOGS_FK]
GO
ALTER TABLE [dbo].[DATASETS]  WITH CHECK ADD  CONSTRAINT [DATASETS_TRAY_LOGS_FK] FOREIGN KEY([TRAYLOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
GO
ALTER TABLE [dbo].[DATASETS] CHECK CONSTRAINT [DATASETS_TRAY_LOGS_FK]
GO
ALTER TABLE [dbo].[DOMAINLOGS]  WITH CHECK ADD  CONSTRAINT [DOMAINLOGS_LOGS_FK1] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[DOMAINLOGS] CHECK CONSTRAINT [DOMAINLOGS_LOGS_FK1]
GO
ALTER TABLE [dbo].[DOMAINLOGS]  WITH CHECK ADD  CONSTRAINT [DOMAINSCALARGROUP_FK] FOREIGN KEY([SCALARGROUP_ID])
REFERENCES [dbo].[SCALARGROUPS] ([SCALARGROUP_ID])
GO
ALTER TABLE [dbo].[DOMAINLOGS] CHECK CONSTRAINT [DOMAINSCALARGROUP_FK]
GO
ALTER TABLE [dbo].[LOGDEPENDENCIES]  WITH CHECK ADD  CONSTRAINT [LOGDEPENDENCIES_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[LOGDEPENDENCIES] CHECK CONSTRAINT [LOGDEPENDENCIES_LOGS_FK]
GO
ALTER TABLE [dbo].[LAYOUTS]  WITH CHECK ADD  CONSTRAINT [LAYOUTS_DATASETS_FK] FOREIGN KEY([DATASET_ID])
REFERENCES [dbo].[DATASETS] ([DATASET_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[LAYOUTS] CHECK CONSTRAINT [LAYOUTS_DATASETS_FK]
GO
ALTER TABLE [dbo].[CLASSIFICATIONS]  WITH CHECK ADD  CONSTRAINT [CLASSIFICATIONS_ALGORITHM_FK1] FOREIGN KEY([ALGORITHMOUTPUT_ID])
REFERENCES [dbo].[ALGORITHMOUTPUTS] ([ALGORITHMOUTPUT_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CLASSIFICATIONS] CHECK CONSTRAINT [CLASSIFICATIONS_ALGORITHM_FK1]
GO
ALTER TABLE [dbo].[CLASSSPECIFICCLASSIFICATIONS]  WITH CHECK ADD  CONSTRAINT [CLASSSPEC_CLASSLOG_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[CLASSLOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CLASSSPECIFICCLASSIFICATIONS] CHECK CONSTRAINT [CLASSSPEC_CLASSLOG_FK]
GO
ALTER TABLE [dbo].[CLASSLOGS]  WITH CHECK ADD  CONSTRAINT [CLASSLOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CLASSLOGS] CHECK CONSTRAINT [CLASSLOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[CLASSLOGS]  WITH CHECK ADD  CONSTRAINT [CLASSSCALARGROUP_FK] FOREIGN KEY([SCALARGROUP_ID])
REFERENCES [dbo].[SCALARGROUPS] ([SCALARGROUP_ID])
GO
ALTER TABLE [dbo].[CLASSLOGS] CHECK CONSTRAINT [CLASSSCALARGROUP_FK]
GO
ALTER TABLE [dbo].[CALIBRATIONLOGS]  WITH CHECK ADD  CONSTRAINT [CALIBRATIONLOGS_LOGS_FK] FOREIGN KEY([LOG_ID])
REFERENCES [dbo].[LOGS] ([LOG_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CALIBRATIONLOGS] CHECK CONSTRAINT [CALIBRATIONLOGS_LOGS_FK]
GO
ALTER TABLE [dbo].[SCALARGROUPS]  WITH CHECK ADD  CONSTRAINT [SCALARGROUPPARENT_FK] FOREIGN KEY([PARENTSCALARGROUP_ID])
REFERENCES [dbo].[SCALARGROUPS] ([SCALARGROUP_ID])
GO
ALTER TABLE [dbo].[SCALARGROUPS] CHECK CONSTRAINT [SCALARGROUPPARENT_FK]
GO
GRANT SELECT ON [dbo].[DECIMALLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DECIMALLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEDECIMALLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEDECIMALLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DECIMALLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DECIMALLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[DECIMALLOGDATA] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEDECIMALLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEDECIMALLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[MASKLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[MASKLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEMASKLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEMASKLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[MASKLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[MASKLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEMASKLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEMASKLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[PROFLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[PROFLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEPROFLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEPROFLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[PROFLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[PROFLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEPROFLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEPROFLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SPECTRALLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SPECTRALLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATESPECTRALLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATESPECTRALLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SPECTRALLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SPECTRALLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATESPECTRALLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATESPECTRALLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SCALARSETS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SCALARSETS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATESCALARSET] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[IMAGELOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[IMAGELOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEIMAGELOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEIMAGELOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[IMAGELOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[IMAGELOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEIMAGELOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT DELETE ON [dbo].[ALGORITHMOUTPUTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT INSERT ON [dbo].[ALGORITHMOUTPUTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[ALGORITHMOUTPUTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT UPDATE ON [dbo].[ALGORITHMOUTPUTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[ALGORITHMOUTPUTS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[CALIBRATIONLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CALIBRATIONLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSLOGDATA] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[DOMAINLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DOMAINLOGDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[DOMAINLOGDATA] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDATAPOINTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDATAPOINTS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[MACHINES] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[MACHINES] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[DATASETS] TO [NVCLANALYST] AS [dbo]
GO
GRANT UPDATE ON [dbo].[DATASETS] TO [NVCLANALYST] AS [dbo]
GO
GRANT DELETE ON [dbo].[DATASETS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DATASETS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[PUBLISHEDDATASETS] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[PUBLISHEDLOGS] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[PUBLISHEDIMAGELOGDATA] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEIMAGELOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DOMAINLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DOMAINLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEDOMAINLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEDOMAINLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEDOMLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDOMAINDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDOMAINDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDOMAINDATA] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDOMAINDATAASBASESPS] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDOMAINDATAASBASESPS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEDOMLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATELOGMASK] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATELOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[TOUCHLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[DELETELOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATELOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOGDEPENDENCIES] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOGDEPENDENCIES] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATELOGDEP] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[DELETELOGDEP] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATELOGDEP] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOCATEDSPECIMENS] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[LAYOUTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[LAYOUTS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATELAYOUT] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[DELETELAYOUT] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATELAYOUT] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[ISLOGOWNER] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[ISLOGOWNER] TO [TSGVIEWER] AS [dbo]
GO
GRANT DELETE ON [dbo].[CLASSIFICATIONS] TO [NVCLANALYST] AS [dbo]
GO
GRANT INSERT ON [dbo].[CLASSIFICATIONS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSIFICATIONS] TO [NVCLANALYST] AS [dbo]
GO
GRANT UPDATE ON [dbo].[CLASSIFICATIONS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSIFICATIONS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSIFICATIONS] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[COMPOUNDMATERIAL] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSSPECIFICCLASSIFICATIONS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSSPECIFICCLASSIFICATIONS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSSPECIFICCLASSIFICATIONS] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATECLASSSPECCLASSIFICATION] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[DELETEALLCLASSIFICATIONS] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATECLASSSPECCLASSIFICATION] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CLASSLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATECLASSLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATECLASSLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATECLASSLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETLOGEXTENTS] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETLOGEXTENTS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATECLASSLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATEDATASET] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SAMPLINGFEATURECOLLECTIONS] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEDATASET] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CALIBRATIONLOGS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[CALIBRATIONLOGS] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATECALIBRATIONLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATECALIBRATIONLOG] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATECALIBRATIONLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETLOGDATAPOINTCOUNT] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETLOGDATAPOINTCOUNT] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[DELETEALLLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATECALIBRATIONLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT DELETE ON [dbo].[ALGORITHMS] TO [NVCLANALYST] AS [dbo]
GO
GRANT INSERT ON [dbo].[ALGORITHMS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[ALGORITHMS] TO [NVCLANALYST] AS [dbo]
GO
GRANT UPDATE ON [dbo].[ALGORITHMS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[ALGORITHMS] TO [TSGVIEWER] AS [dbo]
GO
GRANT DELETE ON [dbo].[SCALARGROUPS] TO [NVCLANALYST] AS [dbo]
GO
GRANT INSERT ON [dbo].[SCALARGROUPS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SCALARGROUPS] TO [NVCLANALYST] AS [dbo]
GO
GRANT UPDATE ON [dbo].[SCALARGROUPS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SCALARGROUPS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[PLSDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[PLSDATA] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[SEARCHFORDATASETS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[SEARCHFORDATASETS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOGTYPES] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[LOGTYPES] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[DATASETSTATS] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[DATASETSTATS] TO [TSGVIEWER] AS [dbo]
GO
GRANT SELECT ON [dbo].[TSARETRAINING] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[TSARETRAINING] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[UPDATETSARETRAINING] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEPLSDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATEDATASETSTATS] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[ISALGORITHMOWNER] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[ISALGORITHMOWNER] TO [TSGVIEWER] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[DELETEPLSDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[OBSERVATIONS] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[CREATETSARETRAINING] TO [NVCLANALYST] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[VALIDATECLASSLOGDATA] TO [NVCLANALYST] AS [dbo]
GO
GRANT SELECT ON [dbo].[GETPUBLISHEDUSERTSA] TO [WEBSERVICE] AS [dbo]
GO
GRANT SELECT ON [dbo].[GETPUBLISHEDSYSTEMTSA] TO [WEBSERVICE] AS [dbo]
GO
GRANT EXECUTE ON [dbo].[GETDOWNSAMPLEDLOGVALUES] TO [WEBSERVICE] AS [dbo]
GO
CREATE TABLE [dbo].[EVENTJOURNAL](
	[DATASET_ID] [varchar](64) NOT NULL,
	[EVENTNUMBER] [int] NOT NULL,
	[CATNO] [int] NOT NULL,
	[ISPRIMARY] [int] NOT NULL,
	[EVENTTIME] [datetime] NULL,
	[TSGPRODUCT] [varchar](100) NULL,
	[TSGUSER] [varchar](4000) NULL,
	[CONTENT] [varchar](4000) NULL,
 CONSTRAINT [PK_EVENTJOURNAL] PRIMARY KEY CLUSTERED 
(
	[DATASET_ID] ASC,
	[EVENTNUMBER] ASC,
	[ISPRIMARY] ASC
))
GO

GRANT DELETE, SELECT ON dbo.EVENTJOURNAL TO NVCLANALYST AS [dbo]
GO

GRANT SELECT ON dbo.EVENTJOURNAL TO TSGVIEWER AS [dbo]
GO

ALTER TABLE [dbo].[EVENTJOURNAL]  WITH CHECK ADD  CONSTRAINT [EVENTJOURNAL_DATASETS_FK] FOREIGN KEY([DATASET_ID])
REFERENCES [dbo].[DATASETS] ([DATASET_ID])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[EVENTJOURNAL] CHECK CONSTRAINT [EVENTJOURNAL_DATASETS_FK]
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[UPDATEEVENTJOURNALEVENT]
  @v_dataset_id varchar(max),
  @v_eventnumber int,
  @v_catno int,
  @v_isprimary int,
  @v_eventtime datetime,
  @v_tsgproduct varchar(100),
  @v_tsguser varchar(max),
  @v_content varchar(max)
AS
BEGIN
  UPDATE dbo.EVENTJOURNAL
     SET
        CATNO=@v_catno,
		EVENTTIME=@v_eventtime,
		TSGPRODUCT=@v_tsgproduct,
		TSGUSER=@v_tsguser,
		CONTENT=@v_content
		WHERE DATASET_ID=@v_dataset_id AND EVENTNUMBER=@v_eventnumber AND ISPRIMARY=@v_isprimary;
END
GO

GRANT EXECUTE ON [dbo].[UPDATEEVENTJOURNALEVENT] TO NVCLANALYST AS [dbo]
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[CREATEEVENTJOURNALEVENT]
  @v_dataset_id varchar(max),
  @v_eventnumber int,
  @v_catno int,
  @v_isprimary int,
  @v_eventtime datetime,
  @v_tsgproduct varchar(100),
  @v_tsguser varchar(max),
  @v_content varchar(max)
AS
BEGIN
DECLARE @eventexists int;
  select @eventexists=count(*) from dbo.EVENTJOURNAL where dataset_id= @v_dataset_id AND EVENTNUMBER= @v_eventnumber AND ISPRIMARY=@v_isprimary;
  if (@eventexists =1)
    EXECUTE dbo.UPDATEEVENTJOURNALEVENT 
	   @V_DATASET_ID=@v_dataset_id,
	   @V_EVENTNUMBER=@v_eventnumber,
	   @V_CATNO=@v_catno,
	   @V_ISPRIMARY=@v_isprimary,
	   @V_EVENTTIME=@v_eventtime,
	   @V_TSGPRODUCT=@v_tsgproduct,
	   @V_TSGUSER=@v_tsguser,
	   @V_CONTENT=@v_content;
  else
     INSERT INTO dbo.EVENTJOURNAL (DATASET_ID, EVENTNUMBER, CATNO, ISPRIMARY, EVENTTIME, TSGPRODUCT, TSGUSER, CONTENT ) VALUES (@v_dataset_id, @v_eventnumber, @v_catno, @v_isprimary, @v_eventtime, @v_tsgproduct, @v_tsguser, @v_content );
END
GO

GRANT EXECUTE ON [dbo].[CREATEEVENTJOURNALEVENT] TO NVCLANALYST AS [dbo]
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[CREATEDOMLOGDATA2]  
	@v_domlog_id varchar(max),
	@v_samplenumber int,
	@v_startvalue float(53),
	@v_endvalue float(53),
	@v_name varchar(max),
	@v_desc varchar(max),
	@v_colour int,
	@v_swirminlist varbinary(max),
	@v_swirtsaversion int,
	@v_vnirminlist varbinary(max),
	@v_vnirtsaversion int,
	@v_tirminlist varbinary(max),
	@v_tirtsaversion int,
	@v_aminlist varbinary(max),
	@v_atsaversion int,
	@v_bminlist varbinary(max),
	@v_btsaversion int
AS 
BEGIN
	INSERT dbo.DOMAINLOGDATA(
		LOG_ID, 
		SAMPLENUMBER, 
		STARTVALUE, 
		ENDVALUE, 
		SAMPLENAME,
		[DESCRIPTION],
		COLOUR,
		SWIRMINLIST,
		SWIRTSAVERSION,
		VNIRMINLIST,
		VNIRTSAVERSION,
		TIRMINLIST,
		TIRTSAVERSION,
		AMINLIST,
		ATSAVERSION,
		BMINLIST,
		BTSAVERSION)
		VALUES (
			@v_domlog_id, 
			@v_samplenumber, 
			@v_startvalue, 
			@v_endvalue, 
			@v_name,
			@v_desc,
			@v_colour,
			@v_swirminlist,
			@v_swirtsaversion,
			@v_vnirminlist,
			@v_vnirtsaversion,
			@v_tirminlist,
			@v_tirtsaversion,
			@v_aminlist,
			@v_atsaversion,
			@v_bminlist,
			@v_btsaversion)
END

GO

GRANT EXECUTE ON [dbo].[CREATEDOMLOGDATA2] TO NVCLANALYST AS [dbo];
GO

EXEC sp_addrolemember N'TSGVIEWER', N'WEBSERVICE'
GO
---------------------------------------------------
--   DATA FOR TABLE LOGTYPES
--   FILTER = none used
---------------------------------------------------
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (0,'domain');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (1,'class');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (2,'decimal');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (3,'image');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (4,'profile');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (5,'spectral');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (6,'mask');
Insert into dbo.LOGTYPES (LOGTYPE_ID,LOGTYPENAME) values (7,'calibration');


---------------------------------------------------
--   DATA FOR TABLE MACHINES
--   FILTER = none used
---------------------------------------------------
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (1,'HyLogger 1','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (7,'HyChips 6-3','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (10,'HyLogger 2-2','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (11,'HyLogger 2-3','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (12,'HyLogger 2-4','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (13,'HyLogger 2-5','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (14,'HyLogger 2-6','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (15,'HyLogger 2-7','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (16,'HyLogger 2-8','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (18,'HyLogger 3-1','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (19,'HyLogger 3-2','urn:cgi:object:CSIRO:HyLogger'); -- WA
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (20,'HyLogger 3-3','urn:cgi:object:CSIRO:HyLogger'); -- SA
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (21,'HyLogger 3-4','urn:cgi:object:CSIRO:HyLogger'); -- NSW
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (22,'HyLogger 3-5','urn:cgi:object:CSIRO:HyLogger'); -- Qld
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (23,'HyLogger 3-6','urn:cgi:object:CSIRO:HyLogger'); -- Tas
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (24,'HyLogger 3-7','urn:cgi:object:CSIRO:HyLogger'); -- NT
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (25,'HyLogger 3-8','urn:cgi:object:CSIRO:HyLogger'); -- Vic
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (2301,'HyLogger 4-2301','urn:cgi:object:CSIRO:HyLogger'); -- NSW
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (2302,'HyLogger 4-2302','urn:cgi:object:CSIRO:HyLogger'); -- SA
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (2303,'HyLogger 4-2303','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (2501,'HyLogger 4-2501','urn:cgi:object:CSIRO:HyLogger');
Insert into dbo.MACHINES (MACHINE_ID,MACHINENAME,NAMESPACEID) values (2502,'HyLogger 4-2502','urn:cgi:object:CSIRO:HyLogger');
