-- Criação do banco de dados
DROP DATABASE IF EXISTS bdcinema;
CREATE DATABASE bdcinema;
USE bdcinema;

-- Tabela de salas de cinema
DROP TABLE IF EXISTS tbsalas;
CREATE TABLE tbsalas (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    numeroSala INT NOT NULL,
    capacidadeMaxima INT NOT NULL,
    capacidadeAtual INT,
    statusSala ENUM('liberada', 'limpeza', 'manutenção', 'em sessão', 'interditada'),
    tipoSala ENUM('comum', '3d', 'imax', 'vip'),
    poltronas SMALLINT
);

INSERT INTO `bdcinema`.`tbsalas`
(
`numerosala`,
`capacidadeMaxima`,
`capacidadeAtual`,
`statusSala`,
`tipoSala`,
`poltronas`)
VALUES
(1,
50,
0,
'liberada',
'3d',
50);


-- Tabela de filmes
DROP TABLE IF EXISTS tbfilmes;
CREATE TABLE tbfilmes (
    id_filme INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    classificacao VARCHAR(25),
    duracao TIME,
    tipoFilme ENUM('nacional', 'estrangeiro'),
    idiomaFilme VARCHAR(20),
    dublagem ENUM('sim', 'não'),
    legenda ENUM('sim', 'não'),
    distribuidor VARCHAR(45)
);
INSERT INTO tbfilmes (titulo, classificacao, duracao, tipoFilme, idiomaFilme, dublagem, legenda, distribuidor)
VALUES ('Matrix', '16 anos', '02:15:00', 'estrangeiro', 'Inglês', 'sim', 'sim', 'Warner Bros');

-- Tabela de gêneros de filme
DROP TABLE IF EXISTS tbgeneros;
CREATE TABLE tbgeneros (
    id_genero INT AUTO_INCREMENT PRIMARY KEY,
    nomeGenero VARCHAR(45),
    descricaoGenero TEXT
);

-- Tabela de sessões
DROP TABLE IF EXISTS tbsessoes;
CREATE TABLE tbsessoes (
    id_sessao INT AUTO_INCREMENT PRIMARY KEY,
    filme_id INT,
    sala_id INT,
    horarioInicio DATETIME,
    duracaoFilme TIME,
    FOREIGN KEY (filme_id) REFERENCES tbfilmes(id_filme),
    FOREIGN KEY (sala_id) REFERENCES tbsalas(id_sala)
);



-- Tabela de clientes
DROP TABLE IF EXISTS tbclientes;
CREATE TABLE tbclientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60),
    email VARCHAR(150) UNIQUE,
    senha VARCHAR(60),
    cpf VARCHAR(14),
    telefone VARCHAR(15),
    perfilFidelidade VARCHAR(60) UNIQUE,
    pontos INT DEFAULT 0
);

INSERT INTO tbclientes (nome, email, senha, cpf, telefone, perfilFidelidade, pontos) VALUES
('Joao', 'a@a.com', 'senha', '23', '55', 'a', 25);

-- Tabela de funcionários
DROP TABLE IF EXISTS tbfuncionarios;
CREATE TABLE tbfuncionarios (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60),
    cargo ENUM('gerente geral', 'gerente de programação', 'bilheteria', 'bomboniere', 'limpeza', 'manutencao', 'administrador ti', 'suporte ti', 'marketing'),
    cpf VARCHAR(14),
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(15)
);

-- Tabela de ingressos
DROP TABLE IF EXISTS tbingressos;
CREATE TABLE tbingressos (
    id_ingresso INT AUTO_INCREMENT PRIMARY KEY,
    sessao_id INT,
    cliente_id INT,
    poltrona SMALLINT,
    valor DECIMAL(10,2),
    tipoIngresso ENUM('comum', '3d', 'imax', 'vip'),
    FOREIGN KEY (sessao_id) REFERENCES tbsessoes(id_sessao),
    FOREIGN KEY (cliente_id) REFERENCES tbclientes(id_cliente)
);

-- Tabela de reservas de poltronas
DROP TABLE IF EXISTS tbreservas;
CREATE TABLE tbreservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    sessao_id INT,
    poltrona SMALLINT,
    dataReserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES tbclientes(id_cliente),
    FOREIGN KEY (sessao_id) REFERENCES tbsessoes(id_sessao)
);

-- Tabela de produtos da bomboniere
DROP TABLE IF EXISTS tbprodutosb;
CREATE TABLE tbprodutosb (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45),
    preco DECIMAL(10,2),
    estoqueDisponivel ENUM('indisponivel', 'disponivel')
);

-- Tabela de compras de produtos
DROP TABLE IF EXISTS tbcompras;
CREATE TABLE tbcompras (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    funcionario_id INT,
    dataCompra DATETIME,
    valorTotal DECIMAL(10,2),
    FOREIGN KEY (cliente_id) REFERENCES tbclientes(id_cliente),    
    FOREIGN KEY (funcionario_id) REFERENCES tbfuncionarios(id_funcionario)
);

-- Itens da compra
DROP TABLE IF EXISTS tbitenscompra;
CREATE TABLE tbitenscompra (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT,
    produto_id INT,
    quantidade INT,
    precoUnitario DECIMAL(10,2),
    FOREIGN KEY (compra_id) REFERENCES tbcompras(id_compra),
    FOREIGN KEY (produto_id) REFERENCES tbprodutosb(id_produto)
);

-- Tabela de chamados técnicos
DROP TABLE IF EXISTS tbchamadostec;
CREATE TABLE tbchamadostec (
    id_chamado INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(500),
    statusChamado ENUM('liberado', 'em andamento', 'em espera'),
    dataAbertura DATE,
    sala_id INT,
    FOREIGN KEY (sala_id) REFERENCES tbsalas(id_sala)
);

-- Tabela de manutenções
DROP TABLE IF EXISTS tbmanutencao;
CREATE TABLE tbmanutencao (
    id_manutencao INT AUTO_INCREMENT PRIMARY KEY,
    sala_id INT,
    descricao TEXT,
    dataInicio DATETIME,
    dataFim DATETIME,
    funcionario_id INT,
    FOREIGN KEY (sala_id) REFERENCES tbsalas(id_sala),
    FOREIGN KEY (funcionario_id) REFERENCES tbfuncionarios(id_funcionario)
);

-- Tabela de campanhas de marketing
DROP TABLE IF EXISTS tbmarketing;
CREATE TABLE tbmarketing (
    id_campanha INT AUTO_INCREMENT PRIMARY KEY,
    nomeCampanha VARCHAR(45),
    inicioCampanha DATETIME,
    fimCampanha DATETIME,
    impactoCampanha SMALLINT
);

-- Tabela de comentários e avaliações de filmes
DROP TABLE IF EXISTS tbcomentariosfilmes;
CREATE TABLE tbcomentariosfilmes (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    filme_id INT,
    comentario TEXT,
    avaliacao TINYINT CHECK (avaliacao BETWEEN 0 AND 10),
    dataComentario DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES tbclientes(id_cliente),
    
    FOREIGN KEY (filme_id) REFERENCES tbfilmes(id_filme)
);



SELECT * FROM bdcinema.tbsalas;
INSERT INTO tbsessoes (filme_id, sala_id, horarioInicio, duracaoFilme)
VALUES (1, 1, '2025-07-06 20:00:00', '01:50:00');

INSERT INTO `bdcinema`.`tbsalas`
(
`numerosala`,
`capacidadeMaxima`,
`capacidadeAtual`,
`statusSala`,
`tipoSala`,
`poltronas`)
VALUES
(10,
50,
0,
'liberada',
'3d',
50);

INSERT INTO `bdcinema`.`tbsalas`
(
`numerosala`,
`capacidadeMaxima`,
`capacidadeAtual`,
`statusSala`,
`tipoSala`,
`poltronas`)
VALUES
(11,
50,
0,
'liberada',
'comum',
50);

SELECT * FROM bdcinema.tbingressos;
SELECT * FROM bdcinema.tbsalas;
SELECT * FROM bdcinema.tbcompras;
SELECT * FROM bdcinema.tbitenscompra;
SELECT * FROM bdcinema.tbreservas;
