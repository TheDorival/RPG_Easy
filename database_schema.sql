-- Script de criação do banco de dados Tormenta 20
-- Execute este script antes de rodar a aplicação

CREATE DATABASE IF NOT EXISTS tormenta20 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE tormenta20;

-- Tabela de Personagens
CREATE TABLE IF NOT EXISTS personagens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nivel INT NOT NULL,
    pontos_vida INT NOT NULL,
    pontos_mana INT NOT NULL,
    defesa INT NOT NULL,
    raca VARCHAR(50) NOT NULL,
    classe VARCHAR(50) NOT NULL,
    origem VARCHAR(50),
    divindade VARCHAR(50),
    forca INT NOT NULL DEFAULT 10,
    destreza INT NOT NULL DEFAULT 10,
    constituicao INT NOT NULL DEFAULT 10,
    inteligencia INT NOT NULL DEFAULT 10,
    sabedoria INT NOT NULL DEFAULT 10,
    carisma INT NOT NULL DEFAULT 10,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_nome (nome),
    INDEX idx_raca (raca),
    INDEX idx_classe (classe)
) ENGINE=InnoDB;

-- Tabela de Perícias dos Personagens
CREATE TABLE IF NOT EXISTS personagem_pericias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    personagem_id INT NOT NULL,
    tipo_pericia VARCHAR(50) NOT NULL,
    treinada BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (personagem_id) REFERENCES personagens(id) ON DELETE CASCADE,
    UNIQUE KEY uk_personagem_pericia (personagem_id, tipo_pericia)
) ENGINE=InnoDB;

-- Tabela de Equipamentos dos Personagens
CREATE TABLE IF NOT EXISTS personagem_equipamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    personagem_id INT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    FOREIGN KEY (personagem_id) REFERENCES personagens(id) ON DELETE CASCADE,
    INDEX idx_tipo (tipo)
) ENGINE=InnoDB;

-- Tabela de Raças
CREATE TABLE IF NOT EXISTS racas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    tamanho VARCHAR(20),
    deslocamento INT,
    bonus_pv INT DEFAULT 0,
    bonus_pm INT DEFAULT 0,
    visao_escuro BOOLEAN DEFAULT FALSE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabela de Modificadores de Atributos das Raças
CREATE TABLE IF NOT EXISTS raca_modificadores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    raca_id INT NOT NULL,
    atributo VARCHAR(20) NOT NULL,
    valor INT NOT NULL,
    FOREIGN KEY (raca_id) REFERENCES racas(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela de Características das Raças
CREATE TABLE IF NOT EXISTS raca_caracteristicas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    raca_id INT NOT NULL,
    caracteristica TEXT NOT NULL,
    FOREIGN KEY (raca_id) REFERENCES racas(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela de Classes
CREATE TABLE IF NOT EXISTS classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    atributo_chave VARCHAR(20) NOT NULL,
    pv_base INT NOT NULL,
    pm_base INT NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabela de Habilidades de Classe
CREATE TABLE IF NOT EXISTS classe_habilidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    classe_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    nivel_requerido INT NOT NULL,
    FOREIGN KEY (classe_id) REFERENCES classes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela de Perícias Disponíveis por Classe
CREATE TABLE IF NOT EXISTS classe_pericias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    classe_id INT NOT NULL,
    tipo_pericia VARCHAR(50) NOT NULL,
    FOREIGN KEY (classe_id) REFERENCES classes(id) ON DELETE CASCADE,
    UNIQUE KEY uk_classe_pericia (classe_id, tipo_pericia)
) ENGINE=InnoDB;

-- Tabela de Origens
CREATE TABLE IF NOT EXISTS origens (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabela de Poderes das Origens
CREATE TABLE IF NOT EXISTS origem_poderes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origem_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    FOREIGN KEY (origem_id) REFERENCES origens(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabela de Magias
CREATE TABLE IF NOT EXISTS magias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    nivel INT NOT NULL,
    escola VARCHAR(50),
    tempo_conjuracao VARCHAR(50),
    alcance VARCHAR(50),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Tabela de Magias dos Personagens
CREATE TABLE IF NOT EXISTS personagem_magias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    personagem_id INT NOT NULL,
    magia_id INT NOT NULL,
    FOREIGN KEY (personagem_id) REFERENCES personagens(id) ON DELETE CASCADE,
    FOREIGN KEY (magia_id) REFERENCES magias(id) ON DELETE CASCADE,
    UNIQUE KEY uk_personagem_magia (personagem_id, magia_id)
) ENGINE=InnoDB;

-- Inserir dados iniciais de exemplo

-- Raças
INSERT INTO racas (nome, descricao, tamanho, deslocamento, bonus_pv, bonus_pm, visao_escuro) VALUES
('Humano', 'Raça versátil e adaptável', 'Médio', 9, 0, 0, FALSE),
('Anão', 'Povo resistente das montanhas', 'Médio', 6, 1, 0, TRUE),
('Elfo', 'Seres longevos e graciosos', 'Médio', 9, 0, 1, FALSE),
('Goblin', 'Criaturas pequenas e engenhosas', 'Pequeno', 6, 0, 0, TRUE);

-- Classes
INSERT INTO classes (nome, descricao, atributo_chave, pv_base, pm_base) VALUES
('Guerreiro', 'Mestre do combate corpo a corpo', 'FORCA', 10, 3),
('Arcanista', 'Conjurador de magias arcanas', 'INTELIGENCIA', 6, 6),
('Bucaneiro', 'Aventureiro ousado e versátil', 'CARISMA', 8, 4),
('Clérigo', 'Devoto divino', 'SABEDORIA', 8, 5);

-- Origens
INSERT INTO origens (nome, descricao) VALUES
('Nobre', 'Nascido na nobreza'),
('Erudito', 'Estudioso dedicado'),
('Devoto', 'Servo dos deuses'),
('Guerreiro de Arena', 'Lutou em arenas pela sobrevivência');

-- Alguns exemplos de magias
INSERT INTO magias (nome, descricao, nivel, escola, tempo_conjuracao, alcance) VALUES
('Bola de Fogo', 'Explosão flamejante que causa dano em área', 3, 'Evocação', '1 ação padrão', '30m'),
('Escudo Arcano', 'Concede +5 na Defesa', 1, 'Abjuração', '1 ação padrão', 'Pessoal'),
('Mísseis Mágicos', 'Dispara projéteis mágicos que acertam automaticamente', 1, 'Evocação', '1 ação padrão', '30m'),
('Curar Ferimentos', 'Restaura pontos de vida', 1, 'Cura', '1 ação padrão', 'Toque');

-- Visualizar estrutura
SHOW TABLES;