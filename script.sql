USE [master]
GO
/****** Object:  Database [ItemDB]    Script Date: 3/24/2021 3:17:31 PM ******/
CREATE DATABASE [ItemDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ItemDB', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\ItemDB.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'ItemDB_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\ItemDB_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [ItemDB] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ItemDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ItemDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ItemDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ItemDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ItemDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ItemDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [ItemDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ItemDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ItemDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ItemDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ItemDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ItemDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ItemDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ItemDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ItemDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ItemDB] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ItemDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ItemDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ItemDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ItemDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ItemDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ItemDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ItemDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ItemDB] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ItemDB] SET  MULTI_USER 
GO
ALTER DATABASE [ItemDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ItemDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ItemDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ItemDB] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [ItemDB] SET DELAYED_DURABILITY = DISABLED 
GO
USE [ItemDB]
GO
/****** Object:  Table [dbo].[ITEMS]    Script Date: 3/24/2021 3:17:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ITEMS](
	[ItemCode] [varchar](50) NOT NULL,
	[ItemName] [nvarchar](50) NOT NULL,
	[SupCode] [varchar](50) NOT NULL,
	[Unit] [varchar](50) NOT NULL,
	[Price] [int] NOT NULL,
	[Supplying] [bit] NOT NULL,
 CONSTRAINT [PK_ITEMS] PRIMARY KEY CLUSTERED 
(
	[ItemCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[SUPPLIERS]    Script Date: 3/24/2021 3:17:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[SUPPLIERS](
	[SupCode] [varchar](50) NOT NULL,
	[SupName] [nvarchar](50) NOT NULL,
	[Address] [nvarchar](50) NOT NULL,
	[Colloborating] [bit] NOT NULL,
 CONSTRAINT [PK_SUPPLIERS] PRIMARY KEY CLUSTERED 
(
	[SupCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT100', N'Head phone', N'SP123', N'Pieces', 70, 1)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT110', N'Webcam', N'SP124', N'Pieces', 250, 0)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT111', N'Key Board', N'SP123', N'Pieces', 50, 1)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT121', N'Gaming Mouse', N'SP101', N'Pieces', 55, 1)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT122', N'Gaming Laptop', N'SP101', N'Pieces', 2000, 1)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT123', N'Main board', N'SP123', N'Block', 150, 1)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT124', N'Graphic Card', N'SP111', N'Pieces', 1000, 1)
INSERT [dbo].[ITEMS] ([ItemCode], [ItemName], [SupCode], [Unit], [Price], [Supplying]) VALUES (N'IT172', N'Pin Laptop', N'SP124', N'Pieces', 500, 1)
INSERT [dbo].[SUPPLIERS] ([SupCode], [SupName], [Address], [Colloborating]) VALUES (N'SP101', N'Vin Group', N'100 Do Ding st, dist 1, HCM city', 0)
INSERT [dbo].[SUPPLIERS] ([SupCode], [SupName], [Address], [Colloborating]) VALUES (N'SP111', N'Ding Dong', N'121 ACDSA st, dist 2, HCM city', 1)
INSERT [dbo].[SUPPLIERS] ([SupCode], [SupName], [Address], [Colloborating]) VALUES (N'SP123', N'Phong Vu', N'123 Main st, dist 9, HCM city', 1)
INSERT [dbo].[SUPPLIERS] ([SupCode], [SupName], [Address], [Colloborating]) VALUES (N'SP124', N'Vinh Phong', N'111 ABC st, dist 7, HCM city', 1)
USE [master]
GO
ALTER DATABASE [ItemDB] SET  READ_WRITE 
GO
