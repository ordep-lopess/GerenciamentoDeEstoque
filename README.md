# 🐾 Sistema de Doações de Alimentos

A Empatinhas nasceu como projeto acadêmico no 4º semestre da graduação, com um propósito social: oferecer às ONGs uma ferramenta simples e eficiente para manter o estoque de doações sempre atualizado e acessível.  

---
## 📌 Script de criação do banco de dados

CREATE DATABASE bancoo;

USE bancoo;

CREATE TABLE doacao (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome_doador VARCHAR(100) NOT NULL COMMENT 'Nome do doador',
  telefone VARCHAR(20) NOT NULL COMMENT 'Telefone do doador',
  email VARCHAR(100) NOT NULL COMMENT 'E-mail do doador',
  descricao VARCHAR(100) NOT NULL COMMENT 'Descrição do alimento doado',
  marca VARCHAR(50) NOT NULL COMMENT 'Marca do produto',
  quantidade DECIMAL(8,2) NOT NULL COMMENT 'Quantidade doada (kg)',
  animal ENUM('cao','gato','aves') NOT NULL COMMENT 'Animal destinatário',
  tipo ENUM('racao','petisco','graos') NOT NULL COMMENT 'Tipo de alimento doado',
  pacote_fechado ENUM('sim','nao') NOT NULL COMMENT 'Pacote está fechado?',
  data_doacao DATE NOT NULL COMMENT 'Data da doação'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE doacao_observacao (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  id_doacao INT(11) NOT NULL,
  observacao VARCHAR(255) NOT NULL,
  CONSTRAINT fk_obs_doacao
    FOREIGN KEY (id_doacao) REFERENCES doacao(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL,
  senha VARCHAR(70) NOT NULL,
  rua VARCHAR(100),
  num VARCHAR(20),
  cep VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE movimentacao (
  id INT AUTO_INCREMENT PRIMARY KEY,
  produto_id INT NULL,
  produto_nome VARCHAR(100) DEFAULT NULL,
  tipo VARCHAR(20) NOT NULL,
  quantidade DOUBLE NOT NULL,
  responsavel VARCHAR(150),
  observacao VARCHAR(500),
  data_movimentacao DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_mov_doacao FOREIGN KEY (produto_id) REFERENCES doacao(id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE movimentacao MODIFY COLUMN produto_id INT NULL;

DROP TRIGGER IF EXISTS trg_doacao_before_delete;

CREATE TRIGGER trg_doacao_before_delete
BEFORE DELETE ON doacao
FOR EACH ROW
UPDATE movimentacao
  SET produto_nome = OLD.descricao,
      produto_id = NULL
WHERE produto_id = OLD.id;


---

## 💡 Funcionalidades

- **Cadastro de usuários:** Registro de conta com nome, e-mail criptografado, senha em hash SHA-256 e endereço (rua, número, CEP).  
- **Autenticação:** Login via e-mail/senha, com criptografia AES para o e-mail e hash seguro para senha.  
- **Logout:** Encerramento de sessão e direcionamento para tela de login.  
- **CRUD de doações:** Permite inserir nova doação (nome do doador, telefone, e-mail, descrição, marca, quantidade, animal, tipo, pacote fechado, data), listar todas as doações em tabela responsiva, editar dados de uma doação existente e excluir doações.  
- **Movimentações:** Registro de entradas e saídas de produtos, com preservação do nome do produto mesmo após exclusão.  
- **Exportação CSV:** Exporta lista de produtos em formato CSV.  
- **Interface responsiva:** Páginas JSP estilizadas com CSS (reset, index, lista, voltar, header) para desktop e mobile.  

---

## 📨 Como usar

1. **Registrar usuário**  
   ![Image](https://github.com/user-attachments/assets/60962a51-bb43-4b23-8d30-1f18c720a8ba)  
   - Acesse `cadastro.jsp`.  
   - Preencha nome, e-mail, senha, réplica de senha, endereço e CEP.  
   - Envie o formulário; a senha será validada e o e-mail criptografado antes de salvar.

2. **Fazer login**  
   ![Image](https://github.com/user-attachments/assets/d5c4ee16-de19-4e8e-8e2e-13281f25c455)
   - Vá em `index.jsp`.  
   - Informe e-mail e senha.  
   - Ao autenticar, é criada a sessão e você será redirecionado para `inicio.jsp`.

3. **Tela de Inicio**
   ![Image](https://github.com/user-attachments/assets/d02c59a6-338e-464d-b8ec-dedbbd4c022e)
   - Essa é nossa página inicial.

4. **Cadastrar uma Doação**  
   ![Image](https://github.com/user-attachments/assets/56818434-2869-4792-8ac8-8e5b76d19c52) 
   - Clique em “Cadastrar” em `inicio.jsp`.  
   - Preencha todos os detalhes da doação (telefone, e-mail, tipo de comida, etc.).  

5. **Cadastrar Entrada/Saída**  
   ![Image](https://github.com/user-attachments/assets/07fa98f1-a456-4c7f-be35-b5b6d092c844)
   - Clique em "Movimentação" em `inicio.jsp`.  
   - Clique em "+ Nova Movimentação".  
   - Selecione se é entrada ou saída e preencha o formulário.  
   - Pode filtrar por data ou tipo de movimentação.  

6. **Listar Doações**  
   ![Image](https://github.com/user-attachments/assets/c5707fc4-4bfc-485d-b049-537f35f14425) 
   - Clique em "Lista" em `inicio.jsp`.  
   - Veja todas as doações em tabela responsiva.  

7. **Consultar Doação Por ID**  
   ![Image](https://github.com/user-attachments/assets/c27f1bf2-a2b9-4a7b-95ca-d062932edbc4)  
   - Clique em "Consultar" em `inicio.jsp`.  
   - Digite o ID do produto que deseja verificar.  

8. **Alterar Doação**  
   ![Image](https://github.com/user-attachments/assets/d518c0d2-90a6-474c-87b7-0233167387e2)
   - Clique em "Alterar" em `inicio.jsp`.  
   - Digite o ID do produto que deseja alterar.  
   - Faça as alterações necessárias e clique em salvar.  

9. **Excluir Doação**  
   ![Image](https://github.com/user-attachments/assets/0124eaa1-ea3a-4a29-9781-7e260c128dc1) 
   - Clique em "Excluir" em `inicio.jsp`.  
   - Digite o ID do produto que deseja excluir.  
   - Importante: ao excluir o produto, as movimentações continuam registradas.  

10. **Exportar para CSV**  
   ![Image](https://github.com/user-attachments/assets/88b548df-eabc-4947-96e7-e6a0196981b2) 
   ![Image](https://github.com/user-attachments/assets/16981638-b21b-4ccf-ada6-b85052df1146)  
   - Clique em "Exportar para CSV".  
   - Será gerado um arquivo com as movimentações/produtos.  

11. **Logout**  
   ![Image](https://github.com/user-attachments/assets/512168d4-17b8-4d7e-ba83-70d46053793c)
   - Clique em “Sair” no cabeçalho.  
   - A sessão será encerrada e você voltará para a tela de login.  

---

## 🛠 Tecnologias e Ferramentas

<div style="display: inline-block">
  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" width="50" height="50"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original.svg" width="50" height="50"/>
  <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original.svg" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/mysql/mysql-original-wordmark.svg" width="50" height="50"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/javascript/javascript-original.svg" width="50" height="50"/>
</div>

