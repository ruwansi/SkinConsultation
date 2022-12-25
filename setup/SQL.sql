-- --------------------------------------------------------------------------------------------
--  CREATE AVAILABILITY TABLE
-- --------------------------------------------------------------------------------------------

--
-- Table structure for table `availability`
--
CREATE TABLE `availability` (
  `id` int(100) NOT NULL,
  `lic_no` varchar(15) NOT NULL,
  `date` date NOT NULL,
  `start` time(6) NOT NULL,
  `end` time(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for table `availability`
--
ALTER TABLE `availability`
  ADD PRIMARY KEY (`id`);
  
--
-- AUTO_INCREMENT for table `availability`
--
ALTER TABLE `availability`
  MODIFY `id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

-- --------------------------------------------------------------------------------------------
--  CREATE CONSULTATION TABLE
-- --------------------------------------------------------------------------------------------
--
-- Table structure for table `consultation`
--

CREATE TABLE `consultation` (
  `booking_id` int(100) NOT NULL,
  `patient_id` varchar(50) NOT NULL,
  `doctor_id` varchar(15) NOT NULL,
  `booked_date` date NOT NULL,
  `start_time` time(6) NOT NULL,
  `end_time` time(6) NOT NULL,
  `cost` int(11) NOT NULL,
  `notes` text NOT NULL,
  `image_data` text NOT NULL,
  `status` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for table `consultation`
--
ALTER TABLE `consultation`
  ADD UNIQUE KEY `booking_id` (`booking_id`);
  
  --
-- AUTO_INCREMENT for table `consultation`
--
ALTER TABLE `consultation`
  MODIFY `booking_id` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

-- --------------------------------------------------------------------------------------------
--  CREATE PATIENT TABLE
-- --------------------------------------------------------------------------------------------
--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `name` varchar(25) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `pid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------------------------------------------
--  DATA DUMPING
-- --------------------------------------------------------------------------------------------
--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`id`, `lic_no`, `date`, `start`, `end`) VALUES
(1, 'M11111', '2022-11-17', '09:00:00.000000', '12:00:00.000000'),
(2, 'M11111', '2022-11-17', '15:00:00.000000', '17:00:00.000000'),
(3, 'M11111', '2022-11-18', '09:00:00.000000', '12:00:00.000000'),
(4, 'M11111', '2022-11-22', '09:00:00.000000', '12:00:00.000000'),
(5, 'M22222', '2022-11-17', '09:00:00.000000', '12:00:00.000000'),
(6, 'M33333', '2022-11-17', '10:00:00.000000', '12:00:00.000000'),
(7, 'M11111', '2022-11-20', '15:00:00.000000', '16:00:00.000000'),
(8, 'M11111', '2022-11-25', '09:00:00.000000', '12:00:00.000000'),
(9, 'M22222', '2022-11-20', '09:00:00.000000', '12:00:00.000000'),
(10, 'M33333', '2022-11-25', '17:00:00.000000', '18:00:00.000000'),
(11, 'M44444', '2022-11-28', '11:00:00.000000', '12:00:00.000000');



