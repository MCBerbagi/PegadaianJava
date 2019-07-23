-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 15, 2019 at 05:38 AM
-- Server version: 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pegadaian`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `Kode_barang` int(11) NOT NULL,
  `Nama_barang` varchar(45) DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `Warna` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`Kode_barang`, `Nama_barang`, `Type`, `Warna`) VALUES
(11, 'laptop', 'elektronik', 'hitam'),
(13, 'dia', 'aku', 'suka'),
(14, 'aku', 'suka', 'dia'),
(15, 'emas', '24 karat', 'emas');

-- --------------------------------------------------------

--
-- Table structure for table `gadai`
--

CREATE TABLE `gadai` (
  `No_gadai` int(11) NOT NULL,
  `Tgl_gadai` date DEFAULT NULL,
  `Jatuh_tempo` date DEFAULT NULL,
  `Jumlah_pinjaman` double DEFAULT NULL,
  `Terbilang` varchar(45) DEFAULT NULL,
  `Tgl_tebusan` date DEFAULT NULL,
  `Jumlah_tebusan` double DEFAULT NULL,
  `Denda` double DEFAULT NULL,
  `Total_tebusan` double DEFAULT NULL,
  `Keterangan` varchar(45) DEFAULT NULL,
  `Barang_Kode_barang` int(11) NOT NULL,
  `Petugas_Nip` int(11) NOT NULL,
  `Nasabah_Ktp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `gadai`
--

INSERT INTO `gadai` (`No_gadai`, `Tgl_gadai`, `Jatuh_tempo`, `Jumlah_pinjaman`, `Terbilang`, `Tgl_tebusan`, `Jumlah_tebusan`, `Denda`, `Total_tebusan`, `Keterangan`, `Barang_Kode_barang`, `Petugas_Nip`, `Nasabah_Ktp`) VALUES
(33, '2017-05-26', '2017-05-27', 90000, '', '2017-05-28', 99000, 5000, 104000, 'Sudah Ditebus', 14, 7, 1),
(34, '2017-05-26', '2017-06-01', 900000, '', '2017-06-06', 995000, 25000, 1020000, 'Sudah Ditebus', 11, 7, 1),
(36, '2017-05-26', '2017-05-28', 200000, '', '2017-05-29', 220000, 10000, 230000, 'Sudah Ditebus', 11, 7, 1),
(37, '2017-05-26', '2017-05-28', 900000, '', '2017-05-29', 995000, 10000, 1005000, 'Sudah Ditebus', 11, 10, 1),
(38, '2017-05-26', '2017-05-27', 900000, '', '2017-05-27', 995000, 0, 995000, 'Sudah Ditebus', 13, 7, 1),
(39, '2017-05-26', '2017-05-27', 1000000, '', '2017-05-27', 1105000, 0, 1105000, 'Sudah Ditebus', 14, 10, 2),
(40, '2017-05-27', '2017-05-27', 900000, '', '2017-05-28', 995000, 10000, 1005000, 'Sudah Ditebus', 14, 7, 1),
(41, '2017-05-27', '2017-05-31', 4555555, '', '2017-05-31', 5016110, 0, 5016110, 'Sudah Ditebus', 13, 7, 1),
(42, '2017-05-28', '2017-05-28', 9000000, '', '0001-01-01', 9905000, 0, 9905000, 'Belum Ditebus', 11, 7, 1),
(43, '2018-10-30', '2018-11-12', 100000, '', '2018-11-13', 110000, 10000, 120000, 'Sudah Ditebus', 11, 7, 1),
(44, '2019-01-15', '2019-01-15', 20000, '', '2019-01-16', 22000, 10000, 32000, 'Sudah Ditebus', 15, 7, 192291928);

-- --------------------------------------------------------

--
-- Table structure for table `nasabah`
--

CREATE TABLE `nasabah` (
  `Ktp` int(11) NOT NULL,
  `Nama` varchar(45) DEFAULT NULL,
  `Alamat` varchar(45) DEFAULT NULL,
  `Hp` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nasabah`
--

INSERT INTO `nasabah` (`Ktp`, `Nama`, `Alamat`, `Hp`) VALUES
(1, 'ahmad', 'sumbawa', '082340245475'),
(2, 'muhammad', 'ada', '3'),
(192291928, 'hasywaihd', 'jbdayway', '910282992730');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `Nip` int(11) NOT NULL,
  `Nama` varchar(45) DEFAULT NULL,
  `alamat` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Hakakses` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`Nip`, `Nama`, `alamat`, `username`, `Password`, `Hakakses`) VALUES
(7, 'catur', 'DEPOK', '1', '1', 'Admin'),
(10, 'mahar', 'ada', '2', '2', 'Kasir'),
(11, 'fira', 'jadjayw', '3', '3', 'Admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`Kode_barang`);

--
-- Indexes for table `gadai`
--
ALTER TABLE `gadai`
  ADD PRIMARY KEY (`No_gadai`),
  ADD KEY `fk_Gadai_Barang1_idx` (`Barang_Kode_barang`),
  ADD KEY `fk_Gadai_Petugas1_idx` (`Petugas_Nip`),
  ADD KEY `fk_Gadai_Nasabah1_idx` (`Nasabah_Ktp`);

--
-- Indexes for table `nasabah`
--
ALTER TABLE `nasabah`
  ADD PRIMARY KEY (`Ktp`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`Nip`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `Kode_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `gadai`
--
ALTER TABLE `gadai`
  MODIFY `No_gadai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;
--
-- AUTO_INCREMENT for table `nasabah`
--
ALTER TABLE `nasabah`
  MODIFY `Ktp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=192291929;
--
-- AUTO_INCREMENT for table `petugas`
--
ALTER TABLE `petugas`
  MODIFY `Nip` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `gadai`
--
ALTER TABLE `gadai`
  ADD CONSTRAINT `fk_Gadai_Barang1` FOREIGN KEY (`Barang_Kode_barang`) REFERENCES `barang` (`Kode_barang`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Gadai_Nasabah1` FOREIGN KEY (`Nasabah_Ktp`) REFERENCES `nasabah` (`Ktp`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Gadai_Petugas1` FOREIGN KEY (`Petugas_Nip`) REFERENCES `petugas` (`Nip`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
