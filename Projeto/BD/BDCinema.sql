-- Criação do banco de dados
DROP DATABASE IF EXISTS BDCinema;
CREATE DATABASE BDCinema;
USE BDCinema;

-- Tabela de Salas de Cinema
DROP TABLE IF EXISTS TBSalas;
CREATE TABLE TBSalas (
    id_sala INT AUTO_INCREMENT PRIMARY KEY,
    numeroSala INT NOT NULL,
    capacidadeMaxima INT NOT NULL,
    capacidadeAtual INT,
    statusSala ENUM('liberada', 'limpeza', 'manutenção', 'em sessão', 'interditada'),
    tipoSala ENUM('Comum','3D', 'IMAX', 'VIP'),
    poltronas SMALLINT
);

-- Tabela de Filmes
DROP TABLE IF EXISTS TBFilmes;
CREATE TABLE TBFilmes (
    id_filme INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    classificacao VARCHAR(25),
    duracao TIME,
    tipoFilme ENUM ('Nacional', 'Estrangeiro'),
    idiomaFilme VARCHAR(20),
    dublagem ENUM ('Sim', 'Não'),
    legenda ENUM ('Sim', 'Não'),
    distribuidor VARCHAR(45)
);

-- Tabela de Gêneros de Filme
DROP TABLE IF EXISTS TBGeneros;
CREATE TABLE TBGeneros (
    id_genero INT AUTO_INCREMENT PRIMARY KEY,
    nomeGenero VARCHAR(45),
    descricaoGenero VARCHAR(100)
);

-- Relacionamento N:N entre Filmes e Gêneros
DROP TABLE IF EXISTS TBFIlmeGenero;
CREATE TABLE TBFIlmeGenero (
    filme_id INT,
    genero_id INT,
    PRIMARY KEY (filme_id, genero_id),
    FOREIGN KEY (filme_id) REFERENCES TBFilmes(id_filme),
    FOREIGN KEY (genero_id) REFERENCES TBGeneros(id_genero)
);

-- Tabela de Sessões
DROP TABLE IF EXISTS TBSessoes;
CREATE TABLE TBSessoes (
    id_sessao INT AUTO_INCREMENT PRIMARY KEY,
    filme_id INT,
    sala_id INT,
    horarioInicio DATETIME,
    duracaoFilme TIME,
    FOREIGN KEY (filme_id) REFERENCES TBFilmes(id_filme),
    FOREIGN KEY (sala_id) REFERENCES TBSalas(id_sala)
);

-- Tabela de Clientes
DROP TABLE IF EXISTS TBClientes;
CREATE TABLE TBClientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60),
    email VARCHAR(150) UNIQUE,
    senha VARCHAR(60),
    cpf VARCHAR(14),
    telefone VARCHAR(15),
    perfilFidelidade VARCHAR(60) UNIQUE,
    pontos INT DEFAULT 0
);

-- Tabela de Funcionários
DROP TABLE IF EXISTS TBFuncionarios;
CREATE TABLE TBFuncionarios (
    id_funcionario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60),
    cargo ENUM('Gerente Geral', 'Gerente de Programação', 'Bilheteria', 'Bomboniere', 'Limpeza', 'Manutencao', 'Administrador TI', 'Suporte TI', 'Marketing'),
    cpf VARCHAR(14),
    email VARCHAR(100) UNIQUE,
    telefone VARCHAR(15)
);

-- Tabela de Ingressos
DROP TABLE IF EXISTS TBIngressos;
CREATE TABLE TBIngressos (
    id_ingresso INT AUTO_INCREMENT PRIMARY KEY,
    sessao_id INT,
    cliente_id INT,
    poltrona SMALLINT,
    valor DECIMAL(10,2),
    tipoIngresso ENUM('Comum','3D', 'IMAX', 'VIP'),
    FOREIGN KEY (sessao_id) REFERENCES TBSessoes(id_sessao),
    FOREIGN KEY (cliente_id) REFERENCES TBClientes(id_cliente)
);

-- Tabela de Reservas de Poltronas
DROP TABLE IF EXISTS TBReservas;
CREATE TABLE TBReservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    sessao_id INT,
    poltrona SMALLINT,
    dataReserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES TBClientes(id_cliente),
    FOREIGN KEY (sessao_id) REFERENCES TBSessoes(id_sessao)
);

-- Tabela de Produtos da Bomboniere
DROP TABLE IF EXISTS TBProdutosB;
CREATE TABLE TBProdutosB (
    id_produto INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45),
    preco DECIMAL(10,2),
    estoqueDisponivel ENUM ('Indisponivel', 'Disponivel')
);

-- Tabela de Compras de Produtos
DROP TABLE IF EXISTS TBCompras;
CREATE TABLE TBCompras (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    funcionario_id INT,
    dataCompra DATETIME,
    valorTotal DECIMAL(10,2),
    FOREIGN KEY (cliente_id) REFERENCES TBClientes(id_cliente),	
    FOREIGN KEY (funcionario_id) REFERENCES TBFuncionarios(id_funcionario)
);

-- Itens da Compra
DROP TABLE IF EXISTS TBItensCompra;
CREATE TABLE TBItensCompra (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT,
    produto_id INT,
    quantidade INT,
    precoUnitario DECIMAL(10,2),
    FOREIGN KEY (compra_id) REFERENCES TBCompras(id_compra),
    FOREIGN KEY (produto_id) REFERENCES TBProdutosB(id_produto)
);

-- Tabela de Chamados Técnicos
DROP TABLE IF EXISTS TBChamadosTec;
CREATE TABLE TBChamadosTec (
    id_chamado INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(500),
    statusChamado ENUM ('Liberado', 'Em andamento', 'Em espera'),
    dataAbertura DATE,
    sala_id INT,
    FOREIGN KEY (sala_id) REFERENCES TBSalas(id_sala)
);

-- Tabela de Manutenções
DROP TABLE IF EXISTS TBManutencao;
CREATE TABLE TBManutencao (
    id_manutencao INT AUTO_INCREMENT PRIMARY KEY,
    sala_id INT,
    descricao TEXT,
    dataInicio DATETIME,
    dataFim DATETIME,
    funcionario_id INT,
    FOREIGN KEY (sala_id) REFERENCES TBSalas(id_sala),
    FOREIGN KEY (funcionario_id) REFERENCES TBFuncionarios(id_funcionario)
);

-- Tabela de Campanhas de Marketing
DROP TABLE IF EXISTS TBMarketing;
CREATE TABLE TBMarketing (
    id_campanha INT AUTO_INCREMENT PRIMARY KEY,
    nomeCampanha VARCHAR(45),
    inicioCampanha DATETIME,
    fimCampanha DATETIME,
    impactoCampanha SMALLINT
);

-- Tabela de Comentários e Avaliações de Filmes
DROP TABLE IF EXISTS TBComentariosFilmes;
CREATE TABLE TBComentariosFilmes (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    filme_id INT,
    comentario TEXT,
    avaliacao TINYINT CHECK (avaliacao BETWEEN 0 AND 10),
    dataComentario DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES TBClientes(id_cliente),
    FOREIGN KEY (filme_id) REFERENCES TBFilmes(id_filme)
);
