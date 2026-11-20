DROP DATABASE IF EXISTS `libman`;
CREATE DATABASE `libman` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `libman`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `tblmember7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `Email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `Phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblmember7` VALUES 
(1,'quanly','quanly123','Phạm Công M','Cầu Giấy, Hà Nội','1985-03-20','quanly@libman.vn','0900000001'),
(2,'thuvien','thuvien123','Lê Anh T','Hà Đông, Hà Nội','1990-07-15','thuvien@libman.vn','0900000002'),
(3,'nguyenvana','bandoc123','Nguyễn Văn A','Thanh Xuân, Hà Nội','2005-10-12','nguyenvana@gmail.com','0987654321'),
(4,'trantuanc','bandoc123','Trần Tuấn C','Đống Đa, Hà Nội','2004-05-02','trantuanc@gmail.com','0987654123'),
(5,'nguyenducd','bandoc123','Nguyễn Đức D','Hai Bà Trưng, Hà Nội','2003-11-30','nguyenducd@gmail.com','0987651234'),
(6,'lehuyh','bandoc123','Lê Huy H','Long Biên, Hà Nội','2004-01-18','lehuyh@gmail.com','0987653214'),
(7,'hoangthik','bandoc123','Hoàng Thị K','Nam Từ Liêm, Hà Nội','2005-02-09','hoangthik@gmail.com','0987000111'),
(8,'buihuug','bandoc123','Bùi Hữu G','Gia Lâm, Hà Nội','1999-06-24','buihuug@gmail.com','0912345678'),
(9,'tranbaol','bandoc123','Trần Bảo L','Việt Trì, Phú Thọ','2005-02-15','tranbaol@gmail.com','0987000199');

CREATE TABLE `tblpublisher7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `publishName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblpublisher7` VALUES 
(1,'NXB Hội Nhà văn'),
(2,'NXB Chính trị quốc gia'),
(3,'NXB Kim Đồng'),
(4,'NXB Văn học');

CREATE TABLE `tblsupplier7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supplierEmail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `supplierPhone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblsupplier7` VALUES 
(1,'Công ty Phát hành sách Hà Nội','Hoàn Kiếm, Hà Nội','sach@phathanhhn.vn','0243000111');

CREATE TABLE `tblreader7` (
  `ID` int(10) NOT NULL,
  `readerCode` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `readerCode` (`readerCode`),
  CONSTRAINT `tblreader7_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `tblmember7` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblreader7` VALUES 
(3,'DG00000003'),
(4,'DG00000004'),
(5,'DG00000005'),
(6,'DG00000006'),
(7,'DG00000007'),
(8,'DG00000008');

CREATE TABLE `tblemployee7` (
  `ID` int(10) NOT NULL,
  `Position` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `tblemployee7_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `tblmember7` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblemployee7` VALUES 
(1,'Quản lý'),
(2,'Nhân viên thư viện');

CREATE TABLE `tbllibrary_employee7` (
  `ID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `tbllibrary_employee7_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `tblemployee7` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tbllibrary_employee7` VALUES (2);

CREATE TABLE `tblmanagement_employee7` (
  `ID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `tblmanagement_employee7_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `tblemployee7` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblmanagement_employee7` VALUES (1);

CREATE TABLE `tbldocument7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `documentName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authors` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publishYear` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publisherID` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `publisherID` (`publisherID`),
  CONSTRAINT `tbldocument7_ibfk_1` FOREIGN KEY (`publisherID`) REFERENCES `tblpublisher7` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tbldocument7` VALUES 
(1,'Rừng Na Uy','Tiểu thuyết','Haruki Murakami',2006,95000,'Tiểu thuyết dịch',1),
(2,'Tư tưởng Hồ Chí Minh','Giáo trình','Nhiều tác giả',2021,60000,'Giáo trình đại học',2),
(3,'Kính vạn hoa','Thiếu nhi','Nguyễn Nhật Ánh',2010,45000,'Truyện dài nhiều tập',3),
(4,'Số đỏ','Tiểu thuyết','Vũ Trọng Phụng',2015,50000,'Tiểu thuyết hiện thực phê phán',4);

CREATE TABLE `tbllibrary_card7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `issueDate` date NOT NULL,
  `expiryDate` date NOT NULL,
  `readerID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `readerID` (`readerID`),
  CONSTRAINT `tbllibrary_card7_ibfk_1` FOREIGN KEY (`readerID`) REFERENCES `tblreader7` (`ID`),
  CONSTRAINT `tbllibrary_card7_chk_1` CHECK ((`expiryDate` > `issueDate`))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tbllibrary_card7` VALUES 
(1,'2025-09-01','2028-08-31',3),
(2,'2025-09-01','2028-08-31',4),
(3,'2025-09-01','2028-08-31',5),
(4,'2025-09-01','2028-08-31',6),
(5,'2026-11-18','2029-11-17',8);

CREATE TABLE `tblborrow_slip7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `borrowDate` date NOT NULL,
  `dueDate` date NOT NULL,
  `readerID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `readerID` (`readerID`),
  KEY `idx_borrow_slip_date` (`borrowDate`),
  CONSTRAINT `tblborrow_slip7_ibfk_1` FOREIGN KEY (`readerID`) REFERENCES `tblreader7` (`ID`),
  CONSTRAINT `tblborrow_slip7_chk_1` CHECK ((`dueDate` >= `borrowDate`))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblborrow_slip7` VALUES 
(1,'2026-08-13','2026-08-20',3),
(2,'2026-08-12','2026-08-19',4),
(3,'2026-08-14','2026-08-21',5),
(4,'2026-08-15','2026-08-22',6),
(5,'2026-07-01','2026-07-08',3);

CREATE TABLE `tblborrow_slip_detail7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `borrowSlipID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `borrowSlipID` (`borrowSlipID`),
  CONSTRAINT `tblborrow_slip_detail7_ibfk_1` FOREIGN KEY (`borrowSlipID`) REFERENCES `tblborrow_slip7` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblborrow_slip_detail7` VALUES 
(1,1),
(2,2),
(3,3),
(4,4),
(5,5);

CREATE TABLE `tblimported_document7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `Quantity` int NOT NULL DEFAULT '1',
  `supplierID` int(10) DEFAULT NULL,
  `documentID` int(10) NOT NULL,
  `borrow_slip_detailID` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uq_slipdetail_document` (`borrow_slip_detailID`,`documentID`),
  KEY `supplierID` (`supplierID`),
  KEY `idx_imported_document` (`documentID`),
  CONSTRAINT `tblimported_document7_ibfk_1` FOREIGN KEY (`supplierID`) REFERENCES `tblsupplier7` (`ID`),
  CONSTRAINT `tblimported_document7_ibfk_2` FOREIGN KEY (`documentID`) REFERENCES `tbldocument7` (`ID`),
  CONSTRAINT `tblimported_document7_ibfk_3` FOREIGN KEY (`borrow_slip_detailID`) REFERENCES `tblborrow_slip_detail7` (`ID`),
  CONSTRAINT `tblimported_document7_chk_1` CHECK (((`borrow_slip_detailID` is null) or (`Quantity` = 1)))
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `tblimported_document7` VALUES 
(1,1,1,1,1),
(2,1,1,2,1),
(3,1,1,3,1),
(4,1,1,4,1),
(5,1,1,1,2),
(6,1,1,2,2),
(7,1,1,3,2),
(8,1,1,1,3),
(9,1,1,2,3),
(10,1,1,1,4),
(11,1,1,4,5),
(12,1,1,1,NULL),
(13,1,1,1,NULL),
(14,1,1,2,NULL),
(15,1,1,2,NULL),
(16,1,1,3,NULL),
(17,1,1,3,NULL),
(18,1,1,4,NULL),
(19,1,1,4,NULL),
(20,1,1,4,NULL);

CREATE TABLE `tblreturn_slip7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `returnDate` date NOT NULL,
  `readerID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `readerID` (`readerID`),
  CONSTRAINT `tblreturn_slip7_ibfk_1` FOREIGN KEY (`readerID`) REFERENCES `tblreader7` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `tblreturn_slip_detail7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `returnSlipID` int(10) NOT NULL,
  `borrowSlipDetailID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `borrowSlipDetailID` (`borrowSlipDetailID`),
  KEY `returnSlipID` (`returnSlipID`),
  CONSTRAINT `tblreturn_slip_detail7_ibfk_1` FOREIGN KEY (`returnSlipID`) REFERENCES `tblreturn_slip7` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `tblreturn_slip_detail7_ibfk_2` FOREIGN KEY (`borrowSlipDetailID`) REFERENCES `tblborrow_slip_detail7` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `tblpenalty_slip7` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `penaltyDate` date NOT NULL,
  `overdue_day` int NOT NULL,
  `fineAmount` double NOT NULL,
  `readerID` int(10) NOT NULL,
  `returnSlipDetailID` int(10) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `returnSlipDetailID` (`returnSlipDetailID`),
  KEY `readerID` (`readerID`),
  CONSTRAINT `tblpenalty_slip7_ibfk_1` FOREIGN KEY (`readerID`) REFERENCES `tblreader7` (`ID`),
  CONSTRAINT `tblpenalty_slip7_ibfk_2` FOREIGN KEY (`returnSlipDetailID`) REFERENCES `tblreturn_slip_detail7` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;