# 🐾 Sistema de Doações de Alimentos

A Empatinhas nasceu como projeto acadêmico no 4º semestre da graduação, com um propósito social: oferecer às ONGs uma ferramenta simples e eficiente para manter o estoque de doações sempre atualizado e acessível.  

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
   <img src="https://github.com/user-attachments/assets/b4f8ce83-ff8e-49d5-8eb9-bdef0a85c165" width="800" height="450" />  
   - Acesse `cadastro.jsp`.  
   - Preencha nome, e-mail, senha, réplica de senha, endereço e CEP.  
   - Envie o formulário; a senha será validada e o e-mail criptografado antes de salvar.

2. **Fazer login**  
   <img src="https://github.com/user-attachments/assets/6f894dc7-0c15-4b65-9530-9215ac727be2" width="800" height="450" />  
   - Vá em `index.jsp`.  
   - Informe e-mail e senha.  
   - Ao autenticar, é criada a sessão e você será redirecionado para `inicio.jsp`.

3. **Cadastrar uma doação**  
   <img src="https://github.com/user-attachments/assets/a531852f-28fe-4d79-9200-0925cf2ae715" width="800" height="450" />  
   - Clique em “Cadastrar” em `inicio.jsp`.  
   - Preencha todos os detalhes da doação (telefone, e-mail, tipo de comida, etc.).  

4. **Cadastrar Entrada/Saída**  
   <img src="https://github.com/user-attachments/assets/16726f44-4ae2-4555-be12-70fcf75a6232" width="800" height="450" />  
   - Clique em "Movimentação" em `inicio.jsp`.  
   - Clique em "+ Nova Movimentação".  
   - Selecione se é entrada ou saída e preencha o formulário.  
   - Pode filtrar por data ou tipo de movimentação.  

5. **Listar Produtos**  
   <img src="https://github.com/user-attachments/assets/988a09d2-4cbc-4a99-b13d-5e029a925232" width="800" height="450" />  
   - Clique em "Lista" em `inicio.jsp`.  
   - Veja todas as doações em tabela responsiva.  

6. **Consultar Produto Por ID**  
   <img src="https://github.com/user-attachments/assets/f218d93b-ec69-4e9d-a333-32f07b106730" width="800" height="450" />  
   - Clique em "Consultar" em `inicio.jsp`.  
   - Digite o ID do produto que deseja verificar.  

7. **Alterar Produtos**  
   <img src="https://github.com/user-attachments/assets/36bda05a-ced3-42ff-a6ef-d246f56632ed" width="800" height="450" />  
   - Clique em "Alterar" em `inicio.jsp`.  
   - Digite o ID do produto que deseja alterar.  
   - Faça as alterações necessárias e clique em salvar.  

8. **Excluir Produtos**  
   <img src="https://github.com/user-attachments/assets/78c4020d-ef80-444f-81f6-380822ee602b" width="800" height="450" />  
   - Clique em "Excluir" em `inicio.jsp`.  
   - Digite o ID do produto que deseja excluir.  
   - Importante: ao excluir o produto, as movimentações continuam registradas.  

9. **Exportar para CSV**  
   <img src="https://github.com/user-attachments/assets/9a0bec7b-5469-4e98-b5f1-4a5dc4d57ba9" width="800" height="450" />  
   <img src="https://github.com/user-attachments/assets/8ce37439-0676-4a26-addb-a78a7ad92b5a" width="800" height="450" />  
   - Clique em "Exportar para CSV".  
   - Será gerado um arquivo com as movimentações/produtos.  

10. **Logout**  
   <img src="https://github.com/user-attachments/assets/860fee25-8c66-4831-b154-b85fa4c82ed8" width="800" height="450" />  
   
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

