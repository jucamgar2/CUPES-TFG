-- encoding: UTF-8
INSERT INTO player(username,password,enabled,birth_date,mail,name) VALUES ('Guaje','$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG',true,'2002-01-02','guaje@guaje.com','Guaje');
INSERT INTO authorities(id,username,authority) VALUES (1,'Guaje','player');
INSERT INTO player(username,password,enabled,birth_date,mail,name) VALUES ('Antonio','$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG',true,'2002-04-04','antonio@antonio.com','Antonio');
INSERT INTO authorities(id,username,authority) VALUES (2,'Antonio','player');
INSERT INTO player(username,password,enabled,birth_date,mail,name) VALUES ('Mera','$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG',true,'2002-05-05','mera@mera.com','Mera');
INSERT INTO authorities(id,username,authority) VALUES (3,'Mera','player');

INSERT INTO player(username,password,enabled,birth_date,mail,name) VALUES ('admin','$2a$10$WpxwP9e/k8VocMGQOWZ8Q.tq0rMaLOhM8U1p6zGYVIVUUwWYVbqDG',true,'2002-01-02','admin@admin.com','admin');
INSERT INTO authorities(id,username,authority) VALUES (4,'admin','admin');

INSERT INTO position(id,x,y) VALUES (1,0,0);
INSERT INTO position(id,x,y) VALUES (2,0,125);
INSERT INTO position(id,x,y) VALUES (3,0,250);
INSERT INTO position(id,x,y) VALUES (4,0,375);
INSERT INTO position(id,x,y) VALUES (6,125,0);
INSERT INTO position(id,x,y) VALUES (7,125,125);
INSERT INTO position(id,x,y) VALUES (8,125,250);
INSERT INTO position(id,x,y) VALUES (9,125,375);
INSERT INTO position(id,x,y) VALUES (11,250,0);
INSERT INTO position(id,x,y) VALUES (12,250,125);
INSERT INTO position(id,x,y) VALUES (13,250,250);
INSERT INTO position(id,x,y) VALUES (14,250,375);
INSERT INTO position(id,x,y) VALUES (16,375,0);
INSERT INTO position(id,x,y) VALUES (17,375,125);
INSERT INTO position(id,x,y) VALUES (18,375,250);
INSERT INTO position(id,x,y) VALUES (19,375,375);

INSERT INTO image(id,name,resource_name,image_type) VALUES (1,'Real Madrid','Madrid','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (2,'Rayo Vallecano','Rayo','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (3,'Fútbol Club Barcelona','Barca','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (4,'Deportivo Alaves','Alaves','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (5,'Athletic Club de Bilbao','Bilbao','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (6,'Real Club Celta de Vigo','Celta','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (7,'Real Club Deportivo Espanyol de Barcelona','Espanyol','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (8,'Unión Deportiva Las Palmas','Palmas','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (9,'Real Club Deportivo Mallorca','Mallorca','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (10,'Club Atlético Osasuna','Osasuna','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (11,'Real Oviedo','Oviedo','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (12,'Real Racing Club de Santander','Racing','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (13,'Real Sociedad','Real','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (14,'Valencia Club de Fútbol','Valencia','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (15,'Real Valladolid Club de Fútbol','Valladolid','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (16,'Villarreal Club de Fútbol','Villarreal','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (17,'Real Zaragoza','Zaragoza','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (18,'Atalanta Bergamasca Calcio','Atalanta','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (19,'Associazione Calcio Milan','Milan','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (20,'Società Sportiva Lazio','Lazio','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (21,'Associazione Sportiva Roma','Roma','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (22,'Hellas Verona Football Club','Verona','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (23,'Società Sportiva Calcio Napoli','Napoli','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (24,'Juventus de Turín','Juve','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (25,'ACF Fiorentina','Fiore','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (26,'Unione Sportiva Lecce','Lecce','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (27,'Inter de Milán','Inter','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (28,'Bologna Football Club','Bologna','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (29,'Udinese Calcio','Udinese','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (30,'Club Atlético de Madrid','ATM','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (31,'Cagliari Calcio','Cagliari','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (32,'Sevilla Fútbol Club','Sevilla','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (33,'Arsenal Football Club','Arsenal','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (34,'Aston Villa Football Club','AVFC','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (35,'Chelsea Football Club','Chelsea','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (36,'Everton Football Club','Everton','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (37,'Liverpool Football Club','Liverpool','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (38,'Manchester City Football Club','City','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (39,'Manchester United Football Club','United','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (40,'Newcastle United Football Club','New','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (41,'Tottenham Hotspur Football Club','Hotspur','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (42,'West Ham United Football Club','Ham','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (43,'Real Sporting de Gijón','Sporting','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (44,'Unión Deportiva Almería','UDA','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (45,'Cádiz Club de Fútbol','Cadiz','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (46,'Racing Club de Ferrol','Ferrol','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (47,'Albacete Balompié','Albacete','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (48,'Sociedad Deportiva Eibar','Eibar','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (49,'Elche Club de Fútbol','Elche','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (50,'Torino Football Club','Torino','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (51,'Club Deportivo Leganés','Leganes','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (52,'Levante Unión Deportiva','Levante','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (53,'Le Havre Athletic Club','LH','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (54,'Racing Club de Lens','Lens','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (55,'Olympique de Lyon','OL','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (56,'Olympique de Marsella','OM','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (57,'Football Club de Metz','Metz','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (58,'Association Sportive de Monaco Football Club','Monaco','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (59,'Montpellier Hérault Sport Club','Montpellier','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (60,'Paris Saint-Germain','PSG','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (61,'Stade Rennes Football Club','Rennes','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (62,'Racing Club de Estrasburgo Alsacia','RSC','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (63,'Bayern de Múnich','Munich','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (64,'Borussia Dortmund','BVB','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (65,'Eintracht Fráncfort','Eintracht','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (66,'SC Friburgo','SCF','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (67,'Club Deportivo Tenerife','CDT','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (68,'Bayer Leverkusen','Bayer','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (69,'VfB Stuttgart','VFB','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (70,'Real Betis Balompié','Betis','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (71,'Getafe Club de Fútbol','Getafe','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (72,'Werder Bremen','Bremen','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (73,'VfL Wolfsburgo','Wolfsburg','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (74,'Burgos Club de Fútbol','Burgos','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (75,'Granada Club de Fútbol','Granada','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (76,'F. C. Colonia','Colonia','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (77,'Fútbol Club Cartagena','Cartagena','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (78,'Toulouse Football Club','Toulouse','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (79,'Lille Olympique Sporting Club','Lille','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (80,'Football Club de Nantes','Nantes','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (81,'VfL Bochum','Bonchum','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (82,'Associazione Calcio Monza','Monza','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (83,'Agrupación Deportiva Alcorcón','ADA','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (84,'Fulham Football Club','Fulham','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (85,'Sociedad Deportiva Amorebieta','SDA','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (86,'Girona Fútbol Club','Girona','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (87,'Club Deportivo Eldense','Eldense','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (88,'Borussia Mönchengladbach','BM','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (89,'Football Club Lorient','Loirent','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (90,'1. F.S.V. Mainz 05','Mainz','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (91,'F. C. Union Berlin','Berlin','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (92,'Club Deportivo Mirandés','Mirandes','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (93,'Stade Brestois 29','Brestois','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (94,'Sociedad Deportiva Huesca','Huesca','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (95,'Unione Sportiva Salernitana 1919','Salernitana','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (96,'Empoli Football Club','Empoli','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (97,'Sheffield United Football Club','Shefield','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (98,'Luton Town Football Club','Luton','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (99,'Crystal Palace Football Club','Crystal','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (100,'Burnley Football Club','Burnley','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (101,'Genoa Cricket & Football Club','Genoa','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (102,'Brentford Football Club','Brentford','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (103,'Olympique Gymnaste Club de Niza','Niza','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (104,'Wolverhampton Wanderers Football Club ','Wolves','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (105,'Stade de Reims','Reims','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (106,'Nottingham Forest Football Club','Forest','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (107,'SV Darmstadt 98','SVD','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (108,'F. C. Augsburgo','FCA','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (109,'TSG 1899 Hoffenheim','TSG','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (110,'Clermont Foot 63','Clermont','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (111,'Unione Sportiva Sassuolo Calcio','Sasuolo','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (112,'1. FC Heidenheim 1846','FCH','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (113,'AFC Bournemouth','AFCB','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (114,'Frosinone Calcio','Frosione','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (115,'Brighton & Hove Albion Football Club','Brighton','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (116,'Fútbol Club Andorra','Andorra','Logo');
INSERT INTO image(id,name,resource_name,image_type) VALUES (117,'RasenBallsport Leipzig','Leipzig','Logo');