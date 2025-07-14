drop database if exists DBTiendaVideojuegos;
create database DBTiendaVideojuegos;
use DBTiendaVideojuegos;

-- Clientes
create table Clientes (
	idCliente int auto_increment, 
    nombreCliente varchar(64), 
    apellidoCliente varchar(64),
    nitCliente varchar(20), 
    correoCliente varchar(64) unique,
    contrasenaCliente varchar(64),
    constraint pk_cliente primary key (idCliente)
); 


-- Empleados
create table Empleados (
	idEmpleado int auto_increment, 
    nombreEmpleado varchar(64), 
    apellidoEmpleado varchar(64), 
    puestoEmpleado varchar(64), 
    telefonoEmpleado varchar(20), 
    correoEmpleado varchar(64),
    sueldoEmpleado double(10,2),
    constraint pk_empleado primary key (idEmpleado)
); 

-- Productos (Videojuegos, Consolas, Accesorios)
create table Productos (
	idProducto int auto_increment, 
    nombreProducto varchar(250), 
    categoria enum('Videojuego', 'Consola', 'Accesorio'),
    plataforma varchar(100),
    precio decimal(6,2),
    descripcion varchar(250),
    constraint pk_producto primary key (idProducto)
); 

-- Ventas
create table Ventas (
	idVenta int auto_increment, 
    idCliente int, 
    idEmpleado int,  
    fechaVenta datetime,
    total double,
    metodoPago enum('Efectivo','Tarjeta'), 
    estadoPago enum('Canelado','Pendiente','Completado'), 
    constraint pk_venta primary key (idVenta),
    constraint fk_venta_cliente foreign key (idCliente) references Clientes(idCliente),
    constraint fk_venta_empleado foreign key (idEmpleado) references Empleados(idEmpleado)
); 

-- Detalle de Ventas
create table DetalleVenta (
	idDetalle int auto_increment, 
    idVenta int, 
    idProducto int, 
    cantidad int,
    constraint pk_detalle primary key (idDetalle),
    constraint fk_detalle_venta foreign key (idVenta) references Ventas(idVenta),
    constraint fk_detalle_producto foreign key (idProducto) references Productos(idProducto)
); 

-- Procedimientos CRUD (sólo ejemplos de Clientes y Productos, los otros son similares)

delimiter $$
create procedure sp_agregarCliente(
    in p_nombreCliente varchar(64), 
    in p_apellidoCliente varchar(64), 
    in p_nitCliente varchar(20),
    in p_correoCliente varchar(64),
    in p_contrasenaCliente varchar(64))
begin 
	insert into Clientes (
        nombreCliente, 
        apellidoCliente, 
        nitCliente, 
        correoCliente, 
        contrasenaCliente
    )
	values(
        p_nombreCliente, 
        p_apellidoCliente, 
        p_nitCliente, 
        p_correoCliente, 
        p_contrasenaCliente
    ); 
end$$
delimiter ;

delimiter //
create procedure sp_agregar_venta (
    in p_idCliente int,
    in p_idEmpleado int,
    in p_fechaVenta datetime,
    in p_total double,
    in p_metodoPago enum('Efectivo','Tarjeta'),
    in p_estadoPago enum('Canelado','Pendiente','Completado')
)
begin
    insert into ventas (idCliente, idEmpleado, fechaVenta, total, metodoPago, estadoPago)
    values (p_idCliente, p_idEmpleado, p_fechaVenta, p_total, p_metodoPago, p_estadoPago);
end;
//
delimiter ;


-- Listar productos
delimiter $$
create procedure sp_listarProductos()
begin
	select
		idProducto as ID,
		nombreProducto as PRODUCTO,
		categoria as CATEGORIA,
		plataforma as PLATAFORMA,
		precio as PRECIO, 
        descripcion as DESCRIPCION
	from Productos;
end$$
delimiter ;

-- Agregar producto
delimiter $$
create procedure sp_agregarProducto(
	in p_nombreProducto varchar(250),
	in p_categoria enum('Videojuego', 'Consola', 'Accesorio'),
	in p_plataforma varchar(100),
	in p_precio decimal(6,2), 
	in p_descripcion varchar(250))
begin
	insert into Productos(nombreProducto, categoria, plataforma, precio, descripcion)
	values(p_nombreProducto, p_categoria, p_plataforma, p_precio, p_descripcion);
end$$
delimiter ;

delimiter //
create procedure sp_actualizar_venta (
    in p_idVenta int,
    in p_idCliente int,
    in p_idEmpleado int,
    in p_fechaVenta datetime,
    in p_total double,
    in p_metodoPago enum('Efectivo','Tarjeta'),
    in p_estadoPago enum('Canelado','Pendiente','Completado')
)
begin
    update ventas
    set idCliente = p_idCliente,
        idEmpleado = p_idEmpleado,
        fechaVenta = p_fechaVenta,
        total = p_total,
        metodoPago = p_metodoPago,
        estadoPago = p_estadoPago
    where idVenta = p_idVenta;
end;
//
delimiter ;


delimiter //
create procedure sp_eliminar_venta (
    in p_idVenta int
)
begin
    delete from ventas
    where idVenta = p_idVenta;
end;
//
delimiter ;


-- Ejemplos de inserciones
call sp_agregarCliente('Juan', 'Martínez', '1234567', 'juan@mail.com', '1234');
call sp_agregarCliente('Laura', 'Hernández', '9876543', 'laura@mail.com', 'laura123');

call sp_agregarProducto('The Legend of Zelda: Tears of the Kingdom', 'Videojuego', 'Nintendo Switch', 69.99, 'Aventura épica en mundo abierto');
call sp_agregarProducto('PlayStation 5', 'Consola', 'Sony', 499.99, 'Consola de nueva generación');
call sp_agregarProducto('Control Inalámbrico Xbox', 'Accesorio', 'Xbox Series X|S', 59.99, 'Control oficial con vibración háptica');

call sp_listarProductos();
