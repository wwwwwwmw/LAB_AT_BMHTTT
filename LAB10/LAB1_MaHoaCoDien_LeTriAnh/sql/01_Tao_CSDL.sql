USE [master];
GO

IF DB_ID(N'MahoacodienDB') IS NULL
BEGIN
    CREATE DATABASE [MahoacodienDB];
END;
GO

USE [MahoacodienDB];
GO

IF OBJECT_ID(N'dbo.history', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.history (
        id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
        created_at DATETIME NOT NULL DEFAULT GETDATE(),
        algorithm NVARCHAR(50) NOT NULL,
        operation NVARCHAR(20) NOT NULL,
        input_text NVARCHAR(MAX) NULL,
        result_text NVARCHAR(MAX) NULL
    );
END;
GO

SELECT DB_NAME() AS ten_database, COUNT(*) AS so_ban_ghi FROM dbo.history;
GO