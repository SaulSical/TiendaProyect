drop database if exists DBTiendaVideojuegos;
create database DBTiendaVideojuegos;
use DBTiendaVideojuegos;

-- Clientes
create table Clientes (
	idCliente int auto_increment, 
    nombreCliente varchar(64), 
    apellidoCliente varchar(64),
    nitCliente varchar(20), 
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
    metodoPago enum('Efectivo','Tarjeta'), 
    estadoPago enum('Pagado','Pendiente'), 
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

-- Agregar cliente
delimiter $$
create procedure sp_agregarCliente(
    in p_nombreCliente varchar(64), 
    in p_apellidoCliente varchar(64), 
    in p_nitCliente varchar(20))
begin 
	insert into Clientes (nombreCliente, apellidoCliente, nitCliente)
	values(p_nombreCliente, p_apellidoCliente, p_nitCliente); 
end$$
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

-- Ejemplos de inserciones
call sp_agregarCliente('Juan', 'Martínez', '1234567');
call sp_agregarCliente('Laura', 'Hernández', '9876543');

call sp_agregarProducto('The Legend of Zelda: Tears of the Kingdom', 'Videojuego', 'Nintendo Switch', 69.99, 'Aventura épica en mundo abierto');
call sp_agregarProducto('PlayStation 5', 'Consola', 'Sony', 499.99, 'Consola de nueva generación');
call sp_agregarProducto('Control Inalámbrico Xbox', 'Accesorio', 'Xbox Series X|S', 59.99, 'Control oficial con vibración háptica');

call sp_listarProductos();
