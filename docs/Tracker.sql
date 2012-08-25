-- MySQL dump 10.11
--
-- Host: localhost    Database: Tracker
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `Application`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `Application` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `description` varchar(512) NOT NULL,
  `LOB` varchar(45) NOT NULL,
  `XMLDescriptor` blob NOT NULL,
  `classMarshall` blob NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `Application`
--

LOCK TABLES `Application` WRITE;
/*!40000 ALTER TABLE `Application` DISABLE KEYS */;
INSERT INTO `Application` VALUES (1,'Stealth','','Equity','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Application name=\"Stealth\" LOB=\"Equity\">\r\n	<MailTo address=\"dg.stealth-support@baml.com\" display=\"DG STEALTH-SUPPORT\" />\r\n	<IssueWorkflow>\r\n		<Create>\r\n			<Impact>\r\n				<Group name=\"User\" groupId=\"cli\">\r\n					<GroupItem name=\"Internal Users\">\r\n						<GrpItmCheckBox desc=\"single\" value=\"intSingle\" severity=\"1\" priority=\"0\" default=\"yes\" />\r\n						<GrpItmCheckBox desc=\"multi\" value=\"intMulti\" severity=\"2\" priority=\"1\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"External clients\">\r\n						<GrpItmCheckBox desc=\"single\" value=\"extSingle\" severity=\"1\" priority=\"0\" />\r\n						<GrpItmCheckBox desc=\"multi\" value=\"extMulti\" severity=\"2\" priority=\"1\" />\r\n					</GroupItem>\r\n				</Group>\r\n				<Group name=\"Market\" groupId=\"mkt\">\r\n					<GroupItem name=\"On Exchanges\" subDesc=\"(TSE, OSE, TostNet, J-Net, OFX, X-Direct, PTS, AXP, SORT, MLXN)\">\r\n						<GrpItmCheckBox desc=\"single flow\" value=\"trhSingle\" severity=\"3\" priority=\"2\" default=\"yes\" />\r\n						<GrpItmCheckBox desc=\"multi flows\" value=\"trhMulti\" severity=\"4\" priority=\"3\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"COB\" subDesc=\"(RAM, Confirms, Oasys, SDI, GMI, XDB)\">\r\n						<GrpItmCheckBox desc=\"single flow\" value=\"cobSingle\" severity=\"5\" priority=\"4\" />\r\n						<GrpItmCheckBox desc=\"multi flows\" value=\"cobMulti\" severity=\"6\" priority=\"5\" />\r\n					</GroupItem>\r\n				</Group>\r\n				<Group name=\"Loss\" groupId=\"loss\">\r\n					<GroupItem name=\"User Inconvenient\">\r\n						<GrpItmCheckBox desc=\"\" value=\"finIncon\" severity=\"7\" priority=\"6\" default=\"yes\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"Financial\">\r\n						<GrpItmCheckBox desc=\"potential\" value=\"finPoten\" severity=\"8\" priority=\"7\" />\r\n						<GrpItmCheckBox desc=\"hard\" value=\"finHard\" severity=\"9\" priority=\"8\" />\r\n					</GroupItem>\r\n				</Group>\r\n				<Group name=\"Outage\" groupId=\"out\" >\r\n					<GroupItem name=\"Functionality impaired\" >\r\n						<GrpItmCheckBox desc=\"\" value=\"impaired\" severity=\"7\" priority=\"6\" default=\"yes\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"Service outage\">\r\n						<GrpItmCheckBox desc=\"single flow\" value=\"outSingle\" severity=\"8\" priority=\"7\" />\r\n						<GrpItmCheckBox desc=\"multi flows\" value=\"outMulti\" severity=\"9\" priority=\"8\" />\r\n					</GroupItem>\r\n				</Group>\r\n				<Group name=\"Incident type\" groupId=\"inc\">\r\n					<GroupItem name=\"Slowness\">\r\n						<GrpItmCheckBox desc=\"\" value=\"slowness\" severity=\"7\" priority=\"6\" default=\"yes\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"Bad data\">\r\n						<GrpItmCheckBox desc=\"\" value=\"baddata\" severity=\"8\" priority=\"7\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"Workflow problem\">\r\n						<GrpItmCheckBox desc=\"\" value=\"workflow\" severity=\"8\" priority=\"7\" />\r\n					</GroupItem>\r\n					<GroupItem name=\"Outage\">\r\n						<GrpItmCheckBox desc=\"\" value=\"outage\" severity=\"8\" priority=\"7\" />\r\n					</GroupItem>\r\n				</Group>\r\n			</Impact>\r\n			<IssueType>\r\n				<Severity sevLow=\"2\" sevMed=\"4\" sevHig=\"7\" prioLow=\"3\" prioMed=\"5\" prioHig=\"8\" priorityWA=\"0.6\" severityWA=\"0.8\" />\r\n				\r\n				<IssueDetails name=\"Issue type\" groupId=\"type\" ctrltype=\"radio\" searchable=\"no\">\r\n					<IssueDesc name=\"New\" id=\"new\"/>\r\n					<IssueDesc name=\"FYI\" id=\"fyi\"/>\r\n					<IssueDesc name=\"Enh\" id=\"enh\"/>\r\n				</IssueDetails>\r\n\r\n				<IssueDetails name=\"New issue\" groupId=\"newIssue\" ctrltype=\"radio\">\r\n					<IssueDesc name=\"Yes\" id=\"yes\"/>\r\n					<IssueDesc name=\"No\" id=\"no\"/>\r\n					<IssueAdditionalInfo name=\"jira\" length=\"20\" showIfOneOf=\"No\" clearOnHide=\"true\" />\r\n				</IssueDetails>\r\n\r\n				<IssueDetails name=\"Request origin\" groupId=\"req\" ctrltype=\"radio\"  searchable=\"yes\">\r\n					<IssueDesc name=\"TP\" id=\"tp\"/>\r\n					<IssueDesc name=\"Ext Cli\" id=\"extCli\"/>\r\n					<IssueDesc name=\"Trader\" id=\"trader\"/>\r\n					<IssueDesc name=\"Ops\" id=\"ops\"/>\r\n					<IssueAdditionalInfo name=\"origin\" length=\"20\" showIfOneOf=\"Ext Cli|Trader|Ops\" clearOnHide=\"true\" />\r\n				</IssueDetails>\r\n\r\n				<IssueDetails name=\"Resolution time\" groupId=\"res\" ctrltype=\"radio\"  searchable=\"no\">\r\n					<IssueDesc name=\"Normal\" id=\"normal\"/>\r\n					<IssueDesc name=\"EOD\" id=\"eod\"/>\r\n					<IssueDesc name=\"ASAP\" id=\"asap\"/>\r\n				</IssueDetails>\r\n\r\n				<IssueDetails name=\"Flow Type\" groupId=\"flow\" ctrltype=\"checkbox\"  searchable=\"yes\">\r\n					<IssueDesc name=\"DMA\" id=\"dma\"/>\r\n					<IssueDesc name=\"DSA\" id=\"dsa\"/>\r\n					<IssueDesc name=\"Fidessa\" id=\"fid\"/>\r\n					<IssueDesc name=\"PT Ag\" id=\"pt\"/>\r\n					<IssueDesc name=\"Prop\" id=\"prop\"/>\r\n				</IssueDetails>\r\n\r\n				<IssueDetails name=\"Location\" groupId=\"loc\" ctrltype=\"checkbox\"  searchable=\"yes\">\r\n					<IssueDesc name=\"JP\" id=\"jp\"/>\r\n					<IssueDesc name=\"AU\" id=\"au\"/>\r\n					<IssueDesc name=\"HK\" id=\"hk\"/>\r\n					<IssueDesc name=\"SG\" id=\"sg\"/>\r\n					<IssueDesc name=\"TW\" id=\"tw\"/>\r\n					<IssueDesc name=\"KR\" id=\"kr\"/>\r\n					<IssueDesc name=\"APR\" id=\"apr\"/>\r\n					<IssueDesc name=\"PacRim\" id=\"pri\"/>\r\n				</IssueDetails>\r\n			</IssueType>\r\n		</Create>\r\n	</IssueWorkflow>\r\n	<Manage>\r\n	</Manage>\r\n</Application>\r\n','');
/*!40000 ALTER TABLE `Application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `AppMailList`
--

DROP TABLE IF EXISTS `AppMailList`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `AppMailList` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `appId` int(10) unsigned NOT NULL,
  `mailTo` varchar(128) NOT NULL,
  `displayName` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `AppMailList`
--

LOCK TABLES `AppMailList` WRITE;
/*!40000 ALTER TABLE `AppMailList` DISABLE KEYS */;
INSERT INTO `AppMailList` VALUES (1,1,'dg.stealth-support@baml.com','DG STEALTH-SUPPORT');
/*!40000 ALTER TABLE `AppMailList` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comment`
--

DROP TABLE IF EXISTS `Comment`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `Comment` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `issueId` int(10) unsigned NOT NULL,
  `typeId` int(10) unsigned NOT NULL,
  `statement` text NOT NULL,
  `submitted` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `submittedBy` varchar(16) NOT NULL,
  `mailSubmittedBy` varchar(64) NOT NULL,
  PRIMARY KEY  (`id`),
  CONSTRAINT `FK_comments_type` FOREIGN KEY (`id`) REFERENCES `CommentType` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `Comment`
--

LOCK TABLES `Comment` WRITE;
/*!40000 ALTER TABLE `Comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `Comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommentType`
--

DROP TABLE IF EXISTS `CommentType`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `CommentType` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `commnetType` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `CommentType`
--

LOCK TABLES `CommentType` WRITE;
/*!40000 ALTER TABLE `CommentType` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommentType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Issue`
--

DROP TABLE IF EXISTS `Issue`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `Issue` (
  `subject` varchar(320) NOT NULL,
  `details` text NOT NULL,
  `submitted` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `acknowledged` timestamp NULL default '0000-00-00 00:00:00',
  `closed` timestamp NULL default '0000-00-00 00:00:00',
  `ackedBy` varchar(16) default NULL,
  `submitBy` varchar(16) NOT NULL,
  `mailSubBy` varchar(64) default NULL,
  `papaId` varchar(32) default NULL,
  `miId` varchar(32) default NULL,
  `objMarshall` blob NOT NULL,
  `id` int(10) unsigned NOT NULL auto_increment,
  `appId` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `issue`
--

LOCK TABLES `Issue` WRITE;
/*!40000 ALTER TABLE `Issue` DISABLE KEYS */;
INSERT INTO `Issue` VALUES ('subject','details','2012-08-06 05:28:28',NULL,NULL,NULL,'nbkhwtv','osvaldo.lucchini@baml.com',NULL,NULL,'rO0ABXNyACJhcHBsaWNhdGlvbnMuU3RlYWx0aC5Jc3N1ZXMuU3VibWl0AAAAAAAAAAECAA5MAANjbGl0ABJMamF2YS9sYW5nL1N0cmluZztMAARmbG93cQB+AAFMAANpbmNxAH4AAUwABGppcmFxAH4AAUwAA2xvY3EAfgABTAAEbG9zc3EAfgABTAADbWt0cQB+AAFMAAhuZXdJc3N1ZXEAfgABTAAGb3JpZ2lucQB+AAFMAANvdXRxAH4AAUwAA3JlcXEAfgABTAADcmVzcQB+AAFMAAR0eXBlcQB+AAFMAAN3aGFxAH4AAXhwdAAJaW50U2luZ2xldAADRE1BdAAId29ya2Zsb3d0AAB0AAJKUHQAB2ZpbkhhcmR0AAh0cmhNdWx0aXQAA1llc3QAAHQACGltcGFpcmVkdAACVFB0AAZOb3JtYWx0AANOZXdw',5,0),('ccc','ddd','2012-08-15 02:50:14',NULL,NULL,NULL,'nbkhwtv','osvaldo.lucchini@baml.com',NULL,NULL,'rO0ABXNyACJhcHBsaWNhdGlvbnMuU3RlYWx0aC5Jc3N1ZXMuU3VibWl0AAAAAAAAAAECAA5MAANjbGl0ABJMamF2YS9sYW5nL1N0cmluZztMAARmbG93cQB+AAFMAANpbmNxAH4AAUwABGppcmFxAH4AAUwAA2xvY3EAfgABTAAEbG9zc3EAfgABTAADbWt0cQB+AAFMAAhuZXdJc3N1ZXEAfgABTAAGb3JpZ2lucQB+AAFMAANvdXRxAH4AAUwAA3JlcXEAfgABTAADcmVzcQB+AAFMAAR0eXBlcQB+AAFMAAN3aGFxAH4AAXhwdAAIZXh0TXVsdGl0AApETUEsIFBUIEFndAAIc2xvd25lc3N0AANhYWF0AAtBVSwgU0csIEFQUnQACGZpblBvdGVudAAJY29iU2luZ2xldAACTm90AANiYmJwdAAGVHJhZGVydAAEQVNBUHQAA05ld3A=',6,0),('ddd','eee','2012-08-15 03:08:30',NULL,NULL,NULL,'nbkhwtv','osvaldo.lucchini@baml.com',NULL,NULL,'rO0ABXNyACJhcHBsaWNhdGlvbnMuU3RlYWx0aC5Jc3N1ZXMuU3VibWl0AAAAAAAAAAECAA5MAANjbGl0ABJMamF2YS9sYW5nL1N0cmluZztMAARmbG93cQB+AAFMAANpbmNxAH4AAUwABGppcmFxAH4AAUwAA2xvY3EAfgABTAAEbG9zc3EAfgABTAADbWt0cQB+AAFMAAhuZXdJc3N1ZXEAfgABTAAGb3JpZ2lucQB+AAFMAANvdXRxAH4AAUwAA3JlcXEAfgABTAADcmVzcQB+AAFMAAR0eXBlcQB+AAFMAAN3aGFxAH4AAXhwdAAIZXh0TXVsdGl0ABNETUEsIEZpZGVzc2EsIFBUIEFndAAGb3V0YWdldAADYWJhdAAOQVUsIEhLLCBUVywgS1J0AAhmaW5Qb3RlbnQACWNvYlNpbmdsZXQAAk5vdAADYmNidAAIaW1wYWlyZWR0AAdFeHQgQ2xpdAADRU9EdAADTmV3cA==',7,1);
/*!40000 ALTER TABLE `Issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Menu`
--

DROP TABLE IF EXISTS `Menu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `Menu` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(24) NOT NULL,
  `tooltip` varchar(128) NOT NULL,
  `level` int(10) unsigned NOT NULL default '0',
  `parentId` int(10) unsigned NOT NULL default '0',
  `action` varchar(64) NOT NULL,
  `isAdmin` char(1) NOT NULL,
  `iconName` varchar(45) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `Menu`
--

LOCK TABLES `Menu` WRITE;
/*!40000 ALTER TABLE `Menu` DISABLE KEYS */;
INSERT INTO `Menu` VALUES (1,'root','no tooltip',0,0,'','0',''),(2,'Admin','Administrative functions',1,1,'','1',''),(3,'Support','Access application support',1,1,'','0',''),(4,'New application','Submit XML for a new application to include in Tracker',2,2,'adminNewApp','1','new.gif'),(5,'User configuration','Update existing users profile',2,2,'adminConfigUser','1','open.gif'),(6,'Equity','Equity technology (HTT, ELT)',2,3,'','0',''),(7,'Stealth','Stealth',3,6,'','0','file.gif'),(9,'MOE','MOE',3,6,'','0','file.gif'),(10,'ETT','Electronic Trading',2,3,'','0',''),(11,'MLXN','Merrill Lynch Crossing Engine',3,10,'supportMLXN','0','file.gif'),(12,'SORT','Smart Order Router',3,10,'supportSORT','0','file.gif'),(13,'AXP','Sa Dio se l\\\'e\\\'',3,10,'supportAXP','0','file.gif'),(14,'Logout','Logoff from App',1,1,'logOff','0',''),(15,'Submit issue','Submit a new issue',4,7,'stealthSubmitForm','0','bug_reporting.gif'),(16,'Search issue','Search issues in the repository',4,7,'stealthSearchForm','0','search.gif');
/*!40000 ALTER TABLE `Menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `Users` (
  `PRILogin` varchar(32) NOT NULL,
  `ALTLogin` varchar(32) NOT NULL,
  `givenName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `employeeId` varchar(16) NOT NULL,
  PRIMARY KEY  (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('lucchosv','nbkhwtv','Osvaldo','Lucchini','osvaldo.lucchini@bac.com',1,'21429921');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-15 10:12:47
