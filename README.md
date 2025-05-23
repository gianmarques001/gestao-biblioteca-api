# 📚 Sistema de Gestão de Biblioteca

Este é um projeto desenvolvido com **Spring Boot** para criar uma API RESTful robusta e intuitiva, focada na gestão de uma biblioteca, incluindo o gerenciamento de livros, usuários, empréstimo e devoluções. 
O projeto utiliza **MySQL** como banco de dados e padrões de projeto como **Strategy**, **Observer** e **Factory Method**.  

## 🚀 Tecnologias Utilizadas

- **Java 21**: Linguagem principal, aproveitando recursos modernos como *records* para código mais conciso e legível.
- **Spring Boot 3.4**: Framework robusto para criação de APIs RESTful, com configuração simplificada e alta produtividade.
  - **Spring Data JPA**: Facilita a integração com bancos de dados relacionais (MySQL e H2), oferecendo manipulação eficiente de dados.
  - **Spring Security**: Garante autenticação e autorização seguras, protegendo os endpoints da API.
  - **Spring Mail (JavaMailSender)**: Envio de e-mails para notificações automáticas, como lembretes de devolução ou confirmações de empréstimo.
  - **Bean Validation**: Valida entradas de dados, assegurando consistência e integridade.
  - **Lombok**: Reduz código boilerplate, melhorando a legibilidade e a produtividade.
- **MySQL**: Banco de dados principal para persistência de dados.
- **H2**: Banco de dados em memória para testes automatizados.
- **MapStruct**: Automatiza mapeamento entre entidades e DTOs, mantendo o código limpo e eficiente.
- **RestAssured**: Facilita testes de integração, garantindo a qualidade da API.

---

## 🧩 Padrões de Projeto Aplicados

Os seguintes padrões de projeto foram implementados para atender às funcionalidades do sistema:

- **Strategy**:
  - **Propósito**: Calcular multas de acordo com o tipo de usuário (ex.: alunos pagam multas diferentes de professores).
  - **Implementação**: Cada tipo de usuário possui uma estratégia específica para cálculo de multas, permitindo flexibilidade e fácil adição de novas regras.

- **Observer**:
  - **Propósito**: Notificar automaticamente usuários sobre reservas e devoluções de livros.
  - **Implementação**: Usuários são registrados como observadores e recebem notificações (ex.: e-mail) quando o status de um empréstimo muda.

- **Factory Method**:
  - **Propósito**: Criar diferentes tipos de notificações (e-mail, SMS, etc.) de forma flexível.
  - **Implementação**: Uma fábrica abstrata cria instâncias de notificações com base no tipo solicitado, garantindo extensibilidade.

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- **Java 21** instalado (JDK).
- **MySQL** configurado localmente ou via Docker.
- Ferramenta para testes de API, como **Postman** ou **Insomnia**.

### Passos para Configuração

1. **Clone o repositório**:
   ```bash
   git clone [https://github.com/seu-usuario/sistema-gestao-biblioteca.git]
   ```

2. **Execute o projeto**:
   - Abra o projeto em sua IDE favorita (ex.: IntelliJ, Eclipse).
   - Execute a classe principal `BibliotecaApplication.java`.

5. **Acesse a API**:
   - A API estará disponível em: [http://localhost:8080](http://localhost:8080).
   - Utilize ferramentas como Postman ou Insomnia para testar os endpoints.

---

## 📍 Endpoints Principais

A API oferece endpoints claros e bem estruturados para gerenciar livro e empréstimos. Abaixo estão alguns exemplos:

| Método  | Endpoint                                     | Descrição                              |
|---------|----------------------------------------------|----------------------------------------|
| `GET`   | `/api/livros`                            | Lista todos os livros.                 |
| `POST`  | `/api/livros`                            | Cria um novo livro.                    |
| `POST`  | `/api/emprestimos`                       | Registra um novo empréstimo.           |
| `PATCH` | `/api/v1/emprestimos/devolver/{idEmprestimo}` | Registra a devolução de um livro.     |
| `GET`   | `/api/v1/emprestimos`             | Lista empréstimos.           |


---

## 🌟 Por que este projeto é especial?

- **Arquitetura robusta**: A combinação de padrões de projeto como Strategy, Observer e Factory Method garante um sistema modular, escalável e fácil de manter.
- **Funcionalidades completas**: Gerencia todo o ciclo de vida de uma biblioteca, desde o cadastro de livros até o envio de notificações automáticas.
- **Notificações com JavaMailSender**: O projeto utiliza o **Spring Mail** com **JavaMailSender** para enviar notificações automáticas por e-mail, como lembretes de devolução ou confirmações de empréstimo.
- **Aprendizado prático**: Demonstra conceitos avançados de programação, como padrões de projeto, integração com bancos de dados relacionais e testes de API.

## 📬 Contribuições e Feedback

Este projeto foi criado com foco em aprendizado e prática. Fico feliz em receber sugestões, críticas ou ideias para melhorias!

---

## 📜 Licença

Distribuído sob a licença **MIT**. Veja mais detalhes em [LICENSE](https://choosealicense.com/licenses/mit/).
