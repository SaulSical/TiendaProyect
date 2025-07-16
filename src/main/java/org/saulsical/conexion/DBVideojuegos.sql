drop database if exists DBTiendaVideojuegos;
create database DBTiendaVideojuegos;
use DBTiendaVideojuegos;

-- TABLAS
create table Clientes (
	idCliente int auto_increment, 
    nombreCliente varchar(64), 
    apellidoCliente varchar(64),
    nitCliente varchar(20), 
    correoCliente varchar(64) unique,
    contrasenaCliente varchar(64),
    constraint pk_cliente primary key (idCliente)
);

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

create table Productos (
	idProducto int auto_increment, 
    nombreProducto varchar(250), 
    categoria enum('Videojuego', 'Consola', 'Accesorio'),
    plataforma varchar(100),
    precio decimal(6,2),
    descripcion varchar(250),
    stock int not null,
    constraint pk_producto primary key (idProducto)
);

create table Ventas (
	idVenta int auto_increment, 
    idCliente int, 
    idEmpleado int,  
    fechaVenta datetime,
    total double,
    metodoPago varchar(100), 
    estadoPago varchar(100),
    constraint pk_venta primary key (idVenta),
    constraint fk_venta_cliente foreign key (idCliente) references Clientes(idCliente) ON DELETE CASCADE,
    constraint fk_venta_empleado foreign key (idEmpleado) references Empleados(idEmpleado) ON DELETE CASCADE
);

create table DetalleVenta (
    idDetalleVenta int auto_increment,
    idVenta int,
    idProducto int,
    precioUnitario decimal(6,2),
    cantidad int,
    subtotal decimal(8,2),
    constraint pk_detalle primary key (idDetalleVenta),
    constraint fk_detalle_venta foreign key (idVenta) references Ventas(idVenta) on delete cascade,
    constraint fk_detalle_producto foreign key (idProducto) references Productos(idProducto) on delete cascade
);

-- PROCEDIMIENTOS ALMACENADOS
delimiter $$

-- Clientes
create procedure sp_agregarCliente(
    in p_nombreCliente varchar(64), 
    in p_apellidoCliente varchar(64), 
    in p_nitCliente varchar(20),
    in p_correoCliente varchar(64),
    in p_contrasenaCliente varchar(64)
)
begin 
	insert into Clientes (
        nombreCliente, apellidoCliente, nitCliente, correoCliente, contrasenaCliente
    )
	values(p_nombreCliente, p_apellidoCliente, p_nitCliente, p_correoCliente, p_contrasenaCliente); 
end $$

create procedure sp_listarClientes()
begin
    select * from Clientes;
end $$

-- Empleados
create procedure sp_agregarEmpleado(
    in p_nombreEmpleado varchar(64),
    in p_apellidoEmpleado varchar(64),
    in p_puestoEmpleado varchar(64),
    in p_telefonoEmpleado varchar(20),
    in p_correoEmpleado varchar(64),
    in p_sueldoEmpleado double
)
begin
    insert into Empleados(nombreEmpleado, apellidoEmpleado, puestoEmpleado, telefonoEmpleado, correoEmpleado, sueldoEmpleado)
    values(p_nombreEmpleado, p_apellidoEmpleado, p_puestoEmpleado, p_telefonoEmpleado, p_correoEmpleado, p_sueldoEmpleado);
end $$

create procedure sp_listarEmpleados()
begin
    select * from Empleados;
end $$

-- Productos
create procedure sp_agregarProducto(
	in p_nombreProducto varchar(250),
	in p_categoria enum('Videojuego', 'Consola', 'Accesorio'),
	in p_plataforma varchar(100),
	in p_precio decimal(6,2), 
	in p_descripcion varchar(250),
    in p_stock int 
)
begin
	insert into Productos(nombreProducto, categoria, plataforma, precio, descripcion,stock)
	values(p_nombreProducto, p_categoria, p_plataforma, p_precio, p_descripcion,p_stock);
end $$

create procedure sp_actualizarProducto (
    in p_idProducto int,
    in p_nombreProducto varchar(250),
    in p_categoria enum('Videojuego', 'Consola', 'Accesorio'),
    in p_plataforma varchar(100),
    in p_precio decimal(6,2),
    in p_descripcion varchar(250),
    in p_stock int
)
begin
    update productos
    set nombreProducto = p_nombreProducto,
        categoria = p_categoria,
        plataforma = p_plataforma,
        precio = p_precio,
        descripcion = p_descripcion,
        stock = p_stock
    where idProducto = p_idProducto;
end $$

create procedure sp_eliminarProducto (
    in p_idProducto int
)
begin
    delete from productos
    where idProducto = p_idProducto;
end $$

create procedure sp_listarProductos()
begin
    select 
        idProducto,
        nombreProducto,
        categoria,
        plataforma,
        precio,
        descripcion,
        stock
    from productos;
end $$

-- Ventas
create procedure sp_agregarVenta (
    in p_idCliente int,
    in p_idEmpleado int,
    in p_fechaVenta datetime,
    in p_total double,
    in p_metodoPago varchar(100),
    in p_estadoPago varchar(100)
)
begin
    insert into Ventas (idCliente, idEmpleado, fechaVenta, total, metodoPago, estadoPago)
    values (p_idCliente, p_idEmpleado, p_fechaVenta, p_total, p_metodoPago, p_estadoPago);
end $$

create procedure sp_actualizarVenta (
    in p_idVenta int,
    in p_idCliente int,
    in p_idEmpleado int,
    in p_fechaVenta datetime,
    in p_total double,
    in p_metodoPago varchar(100),
    in p_estadoPago varchar(100)
)
begin
    update Ventas
    set idCliente = p_idCliente,
        idEmpleado = p_idEmpleado,
        fechaVenta = p_fechaVenta,
        total = p_total,
        metodoPago = p_metodoPago,
        estadoPago = p_estadoPago
    where idVenta = p_idVenta;
end $$

create procedure sp_eliminarVenta (
    in p_idVenta int
)
begin
    delete from Ventas where idVenta = p_idVenta;
end $$

create procedure sp_listarVentas()
begin
    select * from Ventas;
end $$

-- DetalleVenta
create procedure sp_agregarDetalleVenta(
    in p_idVenta int,
    in p_idProducto int,
    in p_precioUnitario decimal(6,2),
    in p_cantidad int,
    in p_subtotal decimal(8,2)
)
begin
    insert into DetalleVenta (idVenta, idProducto, precioUnitario, cantidad, subtotal)
    values (p_idVenta, p_idProducto, p_precioUnitario, p_cantidad, p_subtotal);
end $$

create procedure sp_listarDetalleVentas()
begin
    select 
        dv.idDetalleVenta as id,
        p.idProducto as codigo,
        p.nombreProducto as producto,
        dv.precioUnitario,
        dv.cantidad,
        dv.subtotal
    from DetalleVenta dv
    inner join Productos p on dv.idProducto = p.idProducto;
end $$

create procedure sp_eliminarDetalleVenta(in p_idDetalleVenta int)
begin
    delete from DetalleVenta where idDetalleVenta = p_idDetalleVenta;
end $$

delimiter ;

-- INSERTS DE EJEMPLO
call sp_agregarCliente('Juan', 'Martínez', '1234567', 'juan@mail.com', '1234');
call sp_agregarCliente('Laura', 'Hernández', '9876543', 'laura@mail.com', 'laura123');

call sp_agregarEmpleado('Carlos', 'Lopez', 'Vendedor', '55123456', 'carlos@mail.com', 3000.00);
call sp_agregarEmpleado('Ana', 'Gomez', 'Cajera', '55678901', 'ana@mail.com', 2800.00);

call sp_agregarProducto('The Legend of Zelda: Tears of the Kingdom', 'Videojuego', 'Nintendo Switch', 69.99, 'Aventura épica en mundo abierto', 100);
call sp_agregarProducto('PlayStation 5', 'Consola', 'Sony', 499.99, 'Consola de nueva generación', 100);
call sp_agregarProducto('Control Inalámbrico Xbox', 'Accesorio', 'Xbox Series X|S', 59.99, 'Control oficial con vibración háptica', 100);

call sp_agregarVenta(1, 1, now(), 629.98, 'Tarjeta', 'Completado');
call sp_agregarVenta(2, 2, now(), 69.99, 'Efectivo', 'Pendiente');

call sp_agregarDetalleVenta(1, 1, 69.99, 1, 69.99); -- Zelda
call sp_agregarDetalleVenta(1, 2, 499.99, 1, 499.99); -- PS5
call sp_agregarDetalleVenta(2, 1, 69.99, 1, 69.99); -- Zelda otra venta
