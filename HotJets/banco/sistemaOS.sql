/**
 * Sistema para gestão de OS e Controle
 * @author Fernando Magaton
 */
 
 create database dbsistemaOS;
 use dbsistemaOS;
   
 create table usuarios (
	id int primary key auto_increment,
    nome varchar(50) not null,
    login varchar(15) not null unique,
    senha varchar(250) not null,
    perfil varchar(10) not null
);

insert into usuarios(nome,login,senha,perfil)
values('Administrador','admin', md5('admin'), 'admin');

create table clientes (
	idcli int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50) not null,
    cpf varchar(15) not null,
    cep varchar(10),
    endereco varchar(50) not null,
    numero varchar(10) not null,
    complemento varchar(20),
    bairro varchar(30) not null,
    cidade varchar(30) not null,
    uf char(2) not null
);
    
create table servicos (
	os int primary key auto_increment,
	dataOS timestamp default current_timestamp,
    tipo varchar(200) not null,
    adicional varchar(200) not null,
    valor decimal(10,2),
    idcli int not null,
    foreign key(idcli) references clientes(idcli)
);
    
create table fornecedores (
	idfor int primary key auto_increment,
    razao varchar(50) not null,
    fantasia varchar(50) not null,
    fone varchar(15) not null,
    vendedor varchar(20),
    email varchar(50) not null,
    site varchar(50),
    cnpj varchar(20) not null unique,
    ie varchar(20),
    cep varchar(10),
    endereco varchar(50) not null,
    numero varchar(10) not null,
    complemento varchar(20),
    bairro varchar(30) not null,
    cidade varchar(30) not null,
    uf char(2) not null
);

create table produtos (
	codigo int primary key auto_increment,
    barcode varchar(250) unique,
    produto varchar(50) not null,
    lote varchar(20) not null,
    descricao varchar(250) not null,
    foto longblob,
    fabricante varchar(50),
    dataent timestamp default current_timestamp,
    dataval date not null,
    estoque int not null,
    estoquemin int not null,
    unidade char(2) not null,
    localarm varchar(50),
    custo decimal(10,2) not null,
    lucro decimal(10,2),
    idfor int not null,
    foreign key (idfor) references fornecedores (idfor)
);