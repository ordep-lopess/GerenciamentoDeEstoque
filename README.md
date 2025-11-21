# 🐾 Sistema de Doações de Alimentos

A Empatinhas nasceu como projeto acadêmico no 4º semestre da graduação, com um propósito social: oferecer às ONGs uma ferramenta simples e eficiente para manter o estoque de doações sempre atualizado e acessível. 
---

## 💡 Funcionalidades

- **Cadastro de usuários:** Registro de conta com nome, e-mail criptografado, senha em hash SHA-256 e endereço (rua, número, CEP).  
- **Autenticação:** Login via e-mail/senha, com criptografia AES para o e-mail e hash seguro para senha.  
- **Logout:** Encerramento de sessão e direcionamento para tela de login.  
- **CRUD de doações:** Permite inserir nova doação (nome do doador, telefone, e-mail, descrição, marca, quantidade, animal, tipo, pacote fechado, data), listar todas as doações em tabela responsiva, editar dados de uma doação existente e excluir doações.  
- **Interface responsiva:** Páginas JSP estilizadas com CSS (reset, index, lista, voltar, header) para desktop e mobile.

---

## 🔗 Estrutura do Projeto

Desenvolvido em Java e JSP, este sistema adota o padrão MVC sem recorrer a frameworks externos. Essa abordagem organiza o código em camadas bem definidas, lógica de negócio, persistência de dados e apresentação, garantindo maior manutenibilidade, extensibilidade e escalabilidade.

*   **Model:** Contém os arquivos responsáveis por armazenar, processar e gerenciar informações. Gerencia operações do banco de dados, como armazenamento de usuários.
*   **View:** Contém os arquivos de interface gráfica (o que o cliente visualiza). Inclui arquivos JSP (para páginas dinâmicas com Java), CSS, imagens e Javascript, organizados em subpastas.
*   **Controller:** Atua como intermediário entre Model e View, processando requisições do usuário, gerenciando a lógica do sistema e decidindo ações a serem executadas. Recebe requisições através dos Servlets.
*   **Banco de Dados:** Utiliza o sistema de gerenciamento de banco de dados MySQL.
---

## 📌 Script de criação do banco de dados

CREATE DATABASE bancoo;

USE banco;

CREATE TABLE produto (
  id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nome_doador VARCHAR(100) NOT NULL COMMENT 'Nome do doador',
  telefone VARCHAR(20) NOT NULL COMMENT 'Telefone do doador',
  email VARCHAR(100) NOT NULL COMMENT 'E-mail do doador',
  descricao VARCHAR(100) NOT NULL COMMENT 'Descrição do alimento doado',
  marca VARCHAR(50) NOT NULL COMMENT 'Marca do produto',
  quantidade DECIMAL(8,2) NOT NULL COMMENT 'Quantidade doada (kg)',
  animal ENUM('cao','gato','aves') NOT NULL COMMENT 'Animal destinatário',
  tipo ENUM('racao','petisco', 'graos') NOT NULL COMMENT 'Tipo de alimento doado',
  pacote_fechado ENUM('sim','nao') NOT NULL COMMENT 'Pacote está fechado?',
  data_doacao DATE NOT NULL COMMENT 'Data da doação'
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


CREATE TABLE usuarios ( id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, nome VARCHAR(100) NOT NULL, email VARCHAR(150) NOT NULL, senha VARCHAR(70) NOT NULL, rua VARCHAR(100), num VARCHAR(20), cep VARCHAR(20) );


---

## 📨 Como usar

1. **Registrar usuário**  
   - Acesse `cadastro.jsp`.  
   - Preencha nome, e-mail, senha, réplica de senha, endereço e CEP.  
   - Envie o formulário; a senha será validada e o e-mail criptografado antes de salvar.

3. **Fazer login**  
   - Vá em `index.jsp`.  
   - Informe e-mail e senha.  
   - Ao autenticar, é criada a sessão e você será redirecionado para `inicio.jsp`.

4. **Cadastrar uma doação**  
   - Clique em “Nova Doação” em `inicio.jsp`.  
   - Preencha todos os detalhes da doação (telefone, e-mail, tipo de comida, etc.).  
   - Envie para gravar no banco via `ProdutoDAO`.

5. **Listar / Editar / Excluir**  
   - Em `lista.jsp` veja todas as doações.  
   - Utilize os botões de editar (`alterar.jsp`) ou excluir (`excluirProd.jsp`) conforme precisar.

6. **Logout**  
   - Clique em “Sair” no cabeçalho para invalidar a sessão e voltar ao login.

---

## 🛠 Tecnologias e Ferramentas

<div style="display: inline-block">
  <img align="center" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" height="30" width="40"/>
  <img align="center" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original.svg" height="30" width="40"/>
  <img align="center" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original.svg"   height="30" width="40"/>
  <img align="center" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" height="30" width="40"/>
</div>
