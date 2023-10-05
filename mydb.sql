-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 08, 2022 at 07:25 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `lot`
--

CREATE TABLE `lot` (
  `L_ID` int(1) NOT NULL,
  `L_Date` date NOT NULL,
  `P_ID` int(1) NOT NULL,
  `L_Exp` date NOT NULL,
  `L_Quantity` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `lot`
--

INSERT INTO `lot` (`L_ID`, `L_Date`, `P_ID`, `L_Exp`, `L_Quantity`) VALUES
(1, '2022-11-08', 1, '2023-03-01', 4500),
(2, '2022-11-08', 2, '2023-11-07', 4500),
(3, '2022-11-08', 3, '2022-11-30', 50),
(4, '2022-11-08', 4, '2022-12-10', 100),
(5, '2022-11-08', 5, '2023-01-10', 12),
(6, '2022-11-08', 6, '2023-02-09', 9000),
(7, '2022-11-08', 7, '2023-03-22', 98000),
(8, '2022-11-08', 8, '2023-12-09', 798);

-- --------------------------------------------------------

--
-- Table structure for table `order_product`
--

CREATE TABLE `order_product` (
  `OP_ID` int(1) NOT NULL,
  `OP_Quantity` int(5) NOT NULL,
  `OP_Price` int(7) NOT NULL,
  `P_ID` int(1) NOT NULL,
  `OP_Date` datetime NOT NULL,
  `OP_Type` int(1) NOT NULL DEFAULT 0,
  `OP_Status` int(1) NOT NULL DEFAULT 0,
  `Username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `order_product`
--

INSERT INTO `order_product` (`OP_ID`, `OP_Quantity`, `OP_Price`, `P_ID`, `OP_Date`, `OP_Type`, `OP_Status`, `Username`) VALUES
(1, 5000, 1250000, 1, '2022-11-08 05:19:58', 0, 1, '2'),
(2, 1000, 120000, 8, '2022-11-08 05:38:20', 1, 0, '2'),
(3, 500, 125000, 1, '2022-11-08 08:25:18', 0, 0, 'customer'),
(4, 500, 10000, 5, '2022-11-08 08:25:33', 2, 0, 'customer'),
(5, 100, 3000, 4, '2022-11-08 08:25:45', 0, 0, 'customer'),
(6, 500, 140000, 2, '2022-11-08 11:37:33', 0, 0, '2');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `P_ID` int(1) NOT NULL,
  `P_Name` varchar(15) NOT NULL,
  `P_Quantity` int(5) NOT NULL DEFAULT 0,
  `P_Type` varchar(10) NOT NULL,
  `P_Price` int(4) NOT NULL,
  `P_Image` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`P_ID`, `P_Name`, `P_Quantity`, `P_Type`, `P_Price`, `P_Image`) VALUES
(1, 'Black pepper', 4500, 'spices', 250, 'picture-2022-11-01-22-15-16.png'),
(2, 'Cinnamon', 4500, 'spices', 280, 'picture-2022-11-01-22-21-12.png'),
(3, 'Ginger', 50, 'spices', 25, 'picture-2022-11-01-22-24-05.png'),
(4, 'Galangal', 100, 'spices', 30, 'picture-2022-11-01-22-24-41.png'),
(5, 'Cymbopogon', 12, 'spices', 20, 'picture-2022-11-01-22-26-54.png'),
(6, 'Kaffir lime', 9000, 'vegetables', 50, 'picture-2022-11-01-22-27-22.png'),
(7, 'Chili pepper', 98000, 'spices', 60, 'picture-2022-11-01-22-27-56.png'),
(8, 'Shallot', 798, 'spices', 120, 'picture-2022-11-01-22-28-16.png'),
(9, 'Shallot', 0, 'fruits', 120, 'picture-2022-11-08-12-45-39.png');

-- --------------------------------------------------------

--
-- Table structure for table `promotion`
--

CREATE TABLE `promotion` (
  `PM_ID` int(1) NOT NULL,
  `PM_Name` varchar(20) NOT NULL,
  `PM_Description` varchar(150) NOT NULL,
  `P_ID` int(1) NOT NULL,
  `StartDate` date NOT NULL,
  `EndDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `promotion`
--

INSERT INTO `promotion` (`PM_ID`, `PM_Name`, `PM_Description`, `P_ID`, `StartDate`, `EndDate`) VALUES
(1, 'sale now!!', 'sale 50%', 1, '2022-11-09', '2022-11-16');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `U_Name` varchar(30) NOT NULL,
  `Email` varchar(40) NOT NULL,
  `Phone` char(10) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Role` char(10) NOT NULL,
  `Postcode` char(10) NOT NULL,
  `U_Status` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Username`, `Password`, `U_Name`, `Email`, `Phone`, `Address`, `Role`, `Postcode`, `U_Status`) VALUES
('0', '0', 'Administrator', '-', '-', '-', 'Admin', '-', 0),
('1', '1', 'Manager', '-', '-', '-', 'Manager', '-', 1),
('2', '2', 'Customer', '-', '-', '-', 'Customer', '-', 0),
('4', '4', 'Employee', '-', '-', '-', 'Employee', '-', 0),
('customer', 'customer123', 'Doramon Nobita', 'customer@ku.th', '0812345678', '123 ถนนพหลโยธิน แขวงคลองถนน เขตสายไหม กรุงเทพ', 'Customer', '10220', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `lot`
--
ALTER TABLE `lot`
  ADD PRIMARY KEY (`L_ID`),
  ADD KEY `P_ID` (`P_ID`);

--
-- Indexes for table `order_product`
--
ALTER TABLE `order_product`
  ADD PRIMARY KEY (`OP_ID`),
  ADD KEY `P_ID` (`P_ID`),
  ADD KEY `Username` (`Username`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`P_ID`);

--
-- Indexes for table `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`PM_ID`),
  ADD KEY `P_ID` (`P_ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `lot`
--
ALTER TABLE `lot`
  MODIFY `L_ID` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `order_product`
--
ALTER TABLE `order_product`
  MODIFY `OP_ID` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `P_ID` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `promotion`
--
ALTER TABLE `promotion`
  MODIFY `PM_ID` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `lot`
--
ALTER TABLE `lot`
  ADD CONSTRAINT `lot_ibfk_1` FOREIGN KEY (`P_ID`) REFERENCES `product` (`P_ID`);

--
-- Constraints for table `order_product`
--
ALTER TABLE `order_product`
  ADD CONSTRAINT `order_product_ibfk_1` FOREIGN KEY (`P_ID`) REFERENCES `product` (`P_ID`),
  ADD CONSTRAINT `order_product_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `user` (`Username`);

--
-- Constraints for table `promotion`
--
ALTER TABLE `promotion`
  ADD CONSTRAINT `promotion_ibfk_1` FOREIGN KEY (`P_ID`) REFERENCES `product` (`P_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
