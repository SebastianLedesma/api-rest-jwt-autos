INSERT INTO roles(id,rol_nombre) VALUES (1,'ROLE_ADMIN');
INSERT INTO roles(id,rol_nombre) VALUES (2,'ROLE_USER');

INSERT INTO usuarios(id,nombre,nombre_usuario,email,password) VALUES (1,'admin','ricardo','ricardo@mail.com','$2a$10$aMaKa6nAmTpZ8zZ/B1F9re8.JlAx8CZ52HIJshkdmAdDmhmF6pvua')
INSERT INTO usuarios(id,nombre,nombre_usuario,email,password) VALUES (2,'user','sebastian','sebastian@mail.com','$2a$10$hnCyxRXblMTrq6L8DDz1C.sCs5Bbx.dgXWuxSF0eFAIdaYDE9Hz0y')

INSERT INTO usuario_rol(usuario_id,rol_id) VALUES (1,1)
INSERT INTO usuario_rol(usuario_id,rol_id) VALUES (1,2)
INSERT INTO usuario_rol(usuario_id,rol_id) VALUES (2,2)

INSERT INTO autos(id,color,marca,modelo,precio) VALUES (1,'negro','Honda','Civic',310000)
INSERT INTO autos(id,color,marca,modelo,precio) VALUES (2,'gris','Citroen','C3',215000)
INSERT INTO autos(id,color,marca,modelo,precio) VALUES (3,'blanco','Renault','Kangoo',200000)