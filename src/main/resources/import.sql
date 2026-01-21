INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users(username, password) VALUES ('Mauricio','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('admin','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('Diego','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('Martin','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('Nelson','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');
INSERT INTO users(username, password) VALUES ('ByronN14','$2a$12$1k34YdrmxBkVborQvZLh2OUvX1S80GVVQjZJ5H55y1eez7XV.nV06');


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- user1 with ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- admin with ROLE_ADMIN

-- New User with ROLE_USER

INSERT INTO user_roles (user_id, role_id) VALUES (3, 1); -- Assuming user_id for 'user2' is 3, role_id 1 for ROLE_USER

-- Another User with ROLE_ADMIN

INSERT INTO user_roles (user_id, role_id) VALUES (4, 1); -- Assuming user_id for 'admin2' is 4, role_id 2 for ROLE_ADMIN

-- User with ROLE_USER

INSERT INTO user_roles (user_id, role_id) VALUES (5, 1); -- Assuming user_id for 'guest_user' is 5, role_id 1 for ROLE_USER
INSERT INTO user_roles (user_id, role_id) VALUES (6, 2);

-- Ranking for user1 (assuming user_id = 1)
INSERT INTO ranking (puntos, user_id) VALUES (150, 1);

-- Ranking for admin (assuming user_id = 2) - Admins can also have scores
INSERT INTO ranking (puntos, user_id) VALUES (200, 2);

-- Ranking for user2 (assuming user_id = 3)
INSERT INTO ranking (puntos, user_id) VALUES (100, 3);

-- Ranking for admin2 (assuming user_id = 4)
INSERT INTO ranking (puntos, user_id) VALUES (180, 4);


INSERT INTO ranking (puntos, user_id) VALUES (75, 5);

INSERT INTO curso (nombre_curso, descripcion, dificultad, fecha_registrada) VALUES ('Primeros Pasos en tu Smartphone', 'Este curso básico está diseñado especialmente para adultos mayores que desean familiarizarse con su celular. Aprenderás a encender y apagar el equipo, identificar los íconos principales, hacer y recibir llamadas, enviar mensajes de texto, ajustar el volumen y la luz de la pantalla. Todo con lenguaje sencillo y ejercicios prácticos paso a paso.', 'BÁSICO', '2024-01-15');

INSERT INTO curso (nombre_curso, descripcion, dificultad, fecha_registrada) VALUES ('WhatsApp para la Vida Diaria', 'Este curso te enseñará a usar WhatsApp para comunicarte de forma rápida y sencilla. Aprenderás a enviar y recibir mensajes, fotos, videos, notas de voz y realizar videollamadas. También descubrirás cómo crear grupos familiares, proteger tu cuenta con seguridad y disfrutar esta aplicación sin complicaciones. Ideal para mantenerte cerca de los tuyos.', 'BÁSICO', '2024-02-20');

INSERT INTO curso (nombre_curso, descripcion, dificultad, fecha_registrada) VALUES ('Explorando Internet de forma Segura', 'Aprende a navegar por Internet con tranquilidad. Este curso te mostrará cómo buscar información útil, ingresar a sitios confiables, leer noticias, ver videos y usar el navegador. También conocerás prácticas esenciales para evitar estafas, proteger tu información personal y reconocer señales de alerta en páginas y correos sospechosos. Tu seguridad es prioridad.', 'INTERMEDIO', '2024-03-10');

INSERT INTO curso (nombre_curso, descripcion, dificultad, fecha_registrada) VALUES ('Videollamadas con Familiares y Amigos', 'Conecta con tus seres queridos desde cualquier lugar a través de videollamadas. En este curso aprenderás a usar plataformas como Zoom y Google Meet para iniciar o unirte a reuniones virtuales. Te enseñaremos a encender la cámara, el micrófono, compartir pantalla y usar funciones básicas. Mantente cerca de tu familia, estés donde estés.', 'BÁSICO', '2024-04-05');

INSERT INTO curso (nombre_curso, descripcion, dificultad, fecha_registrada) VALUES ('Trámites Online Esenciales (Banca y Salud)', 'Realiza tus trámites más importantes sin salir de casa. Este curso te enseña a utilizar plataformas bancarias para consultar saldos, hacer transferencias y pagar servicios. Además, aprenderás a ingresar a sistemas de salud, solicitar citas médicas y ver tus resultados. Todo con explicaciones paso a paso para que ganes confianza digitalmente.', 'INTERMEDIO', '2024-05-25');

INSERT INTO curso (nombre_curso, descripcion, dificultad, fecha_registrada) VALUES ('Redes Sociales: Conéctate y Comparte', 'Descubre cómo conectarte con amigos y familia a través de las redes sociales. En este curso aprenderás a crear tu perfil en Facebook e Instagram, subir fotos, escribir publicaciones, dar “me gusta” y comentar. También conocerás cómo proteger tu cuenta y controlar quién puede ver tu información. Comparte momentos y mantente al día.', 'INTERMEDIO', '2024-06-30');


-- Lecciones para 'Primeros Pasos en tu Smartphone' (id_curso = 1)
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Conociendo tu Teléfono: Botones y Pantalla', 'Identificación de botones, manejo de la pantalla táctil y gestos básicos.', 'https://youtu.be/OFrhaOMz52A?si=s-yOvCZsfEOwm7ht', 1);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Hacer y Recibir Llamadas', 'Cómo marcar números, guardar contactos y responder llamadas entrantes.', 'https://youtu.be/rUrzGiJj-B0?si=3LTY6q81n_XX2k9Z', 1);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Enviar y Recibir Mensajes de Texto (SMS)', 'Escribir, enviar y leer mensajes de texto de forma sencilla.', 'https://youtu.be/Rd9K7EkL3hA?si=nh1BT_IGmVje0jJW', 1);

-- Lecciones para 'WhatsApp para la Vida Diaria' (id_curso = 2)
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Instalar y Configurar WhatsApp', 'Pasos para descargar, instalar y configurar tu cuenta de WhatsApp.', 'https://youtu.be/cSI3fn9Io2M?si=6nMjPKRLWTJ9Z7Bt', 2);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Enviar Mensajes y Fotos', 'Cómo chatear, enviar mensajes de voz y compartir fotos y videos.', 'https://www.youtube.com/watch?v=h4sBACB4Esc', 2);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Videollamadas y Llamadas de Voz por WhatsApp', 'Realizar videollamadas grupales e individuales para hablar con tus contactos.', 'https://youtu.be/AhBeOHQsP_o?si=wdLuaKsr8-nt_Vjh', 2);

-- Lecciones para 'Explorando Internet de forma Segura' (id_curso = 3)
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('¿Qué es Internet y Cómo Funciona?', 'Conceptos básicos de la red, navegadores web y cómo buscar información.', 'https://youtu.be/rw41W8crZ_Y?si=Md5huaGrCT_M5WPE', 3);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Identificando Sitios Seguros y Evitando Estafas', 'Consejos para reconocer páginas web seguras (HTTPS) y evitar engaños comunes.', 'https://youtu.be/jmr6aJKh0jQ?si=zKp883ewJUMx2FZV', 3);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Configurar tu Correo Electrónico (Email)', 'Creación y uso de una cuenta de correo electrónico para comunicarte online.', 'https://youtu.be/JfRzLkYUhsM?si=2A2XcW18IbAqWzBw', 3);

-- Lecciones para 'Videollamadas con Familiares y Amigos' (id_curso = 4)
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Unirse a una Videollamada (Zoom/Meet)', 'Pasos para aceptar invitaciones y participar en reuniones virtuales.', 'https://youtu.be/CX-mLhcC_go?si=F64mYh9a-AFaAlte', 4);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Funciones Básicas: Micrófono, Cámara y Chat', 'Controlar el audio, video y usar el chat durante una videollamada.', 'https://youtu.be/caBh1d4neeI?si=mJDC1keCzopYt93l', 4);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Crear una Videollamada Propia', 'Cómo iniciar una videollamada y enviar invitaciones a tus contactos.', 'https://youtu.be/KLlTp0zNqnE?si=Cek0M_2aReHOhb0z', 4);

-- Lecciones para 'Trámites Online Esenciales (Banca y Salud)' (id_curso = 5)
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Banca Online: Consulta de Saldos y Movimientos', 'Acceder a tu banco por internet para ver saldos y transacciones de forma segura.', 'https://youtu.be/AJhDlxzShMY?si=R4CuJBwdV8UkxAmH', 5);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Citas Médicas Digitales', 'Cómo solicitar y gestionar citas con el médico a través de plataformas online.', 'https://youtu.be/FE5kwEQ533U?si=QOqxUbyGom_Od5In', 5);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Pagos de Servicios Básicos Online', 'Pagar recibos de luz, agua o teléfono de forma sencilla y segura desde tu casa.', 'https://youtu.be/Nyq8xVEC3sk?si=pqzweMdboLywfd7M', 5);


-- Lecciones para 'Redes Sociales: Conéctate y Comparte' (id_curso = 6)
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Crear y Configurar tu Perfil en Facebook', 'Pasos iniciales para unirte a Facebook y personalizar tu perfil.', 'https://youtu.be/Fr9WLAtU_O0?si=mfCJ7Yv2d8erHGYr', 6);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Explorando Fotos y Videos en Instagram', 'Cómo ver publicaciones, seguir a amigos y compartir imágenes de forma básica.', 'https://youtu.be/DoHaQP_-DKI?si=-oBaIM7sGqPoY4dI', 6);
INSERT INTO leccion (titulo, contenido, video_url, id_curso) VALUES ('Comunicación con Amigos y Familiares', 'Enviar mensajes privados, reaccionar a publicaciones y comentar.', 'https://youtu.be/zjdV_zdrhGw?si=8AAWRzj8Z585aoLj', 6);



-- Progreso de Juan García (id_usuario = 1)
-- Insignias de Juan: 1 (Explorador Digital), 3 (Navegante Seguro), 7 (Aprendiz Incansable)
-- Progreso de Juan Pérez (id_user = 1)
-- Insignias de Juan: 1 (Explorador Digital), 3 (Navegante Seguro), 7 (Aprendiz Incansable)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (1, 1, 'COMPLETADO', '2024-01-20'); -- Por Insignia 1
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (1, 3, 'EN_PROGRESO', '2024-02-15'); -- Por Insignia 3
-- Añadimos un tercer curso completado para justificar la insignia 7 'Aprendiz Incansable'
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (1, 4, 'COMPLETADO', '2024-03-01'); -- Juan completó 'Videollamadas' (ejemplo para Aprendiz Incansable)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (1, 2, 'EN_PROGRESO', '2024-04-10'); -- Otro curso en progreso

-- Progreso de María Luna (id_user = 2)
-- Insignias de María: 2 (Maestro de la Comunicación), 4 (Conectado con los Tuyos), 6 (Socializador Online), 7 (Aprendiz Incansable)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (2, 2, 'COMPLETADO', '2024-01-25'); -- Por Insignia 2
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (2, 4, 'COMPLETADO', '2024-03-05'); -- Por Insignia 4
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (2, 6, 'EN_PROGRESO', '2024-04-20'); -- Por Insignia 6
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (2, 1, 'EN_PROGRESO', '2024-05-10'); -- Tercer curso completado para Insignia 7

-- Progreso de Carlos Paredes (id_user = 3)
-- Insignias de Carlos: 1 (Explorador Digital), 5 (Ciudadano Digital), 8 (Pionero de la Tecnología)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (3, 1, 'COMPLETADO', '2024-02-01'); -- Por Insignia 1
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (3, 5, 'COMPLETADO', '2024-03-15'); -- Por Insignia 5 (también justifica Insignia 8, ya que 'Trámites Online' es INTERMEDIO)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (3, 3, 'EN_PROGRESO', '2024-04-25'); -- Otro curso en progreso

-- Progreso de Ana del Rey (id_user = 4)
-- Insignias de Ana: 2 (Maestro de la Comunicación), 3 (Navegante Seguro)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (4, 2, 'COMPLETADO', '2024-01-30'); -- Por Insignia 2
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (4, 3, 'EN_PROGRESO', '2024-02-28'); -- Por Insignia 3

-- Progreso de Diego Pérez (id_user = 5)
-- Insignias de Diego: 4 (Conectado con los Tuyos), 6 (Socializador Online)
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (5, 4, 'COMPLETADO', '2024-03-10'); -- Por Insignia 4
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (5, 6, 'COMPLETADO', '2024-04-01'); -- Por Insignia 6
INSERT INTO progreso_curso_usuario (id_user, id_curso, estado, fecha_inscripcion) VALUES (5, 1, 'EN_PROGRESO', '2024-05-01'); -- Otro curso iniciado

INSERT INTO asesor (nombre_completo, correo, telefono, dni, id_curso) VALUES ('María López', 'maria.lopez@example.com', '987654321', '12345678', 1);

INSERT INTO asesor (nombre_completo, correo, telefono, dni, id_curso) VALUES ('Carlos Rivera', 'carlos.rivera@example.com', '912345678', '23456789', 2);

INSERT INTO asesor (nombre_completo, correo, telefono, dni, id_curso) VALUES ('Lucía Fernández', 'lucia.fernandez@example.com', '998877665', '34567890', 3);

INSERT INTO asesor (nombre_completo, correo, telefono, dni, id_curso) VALUES ('José Martínez', 'jose.martinez@example.com', '955443322', '45678901', 4);

INSERT INTO asesor (nombre_completo, correo, telefono, dni, id_curso) VALUES ('Ana Torres', 'ana.torres@example.com', '933221100', '56789012', 5);

INSERT INTO asesor (nombre_completo, correo, telefono, dni, id_curso) VALUES ('Pedro Gutiérrez', 'pedro.gutierrez@example.com', '944556677', '67890123', 6);
