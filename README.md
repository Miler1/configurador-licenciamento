# Configurador do Licenciamento Ambiental

## Tecnologias utilizadas

```bash
JAVA 14
Postgres 12
Node 14.6.0
NPM 6.14.6
```

# Configuração ambiente de desenvolvimento

- Clonar o projeto para máquina de desenvolvimento


## Frontend
### NPM
Atualize seu `NPM` para última versão (note que a `6.14.6*` era a última quando se iniciou o projeto, mas se sempre utlizar a atual não teremos problemas), para isso
```bash
npm install npm@latest -g
```

### Node
Para gerenciar as versões do `Node`, recomendamos utilizar o 'n'
Leia sobre ele e sua instalação no link -> https://github.com/tj/n

Após instalar o 'n', faça a instalação da versão `14.6.0*` do `Node`
```bash
n 14.6.0
``` 

### Baixar dependências
Via terminal entre no diretorio `{dir-projeto}/frontend` e execute:
```bash
npm install
```

### Executar
Via terminal entre no diretorio `{dir-projeto}/frontend` e execute:
```bash
npm run serve
```


## Backend
Utilizar `java` na versão `14*`

### Instalação Maven

Instalação maven no diretório `/opt`
```bash
sudo mkdir -p /opt/ferramentas
```

```bash
cd /opt/ferramentas
```

Download
```bash
sudo wget http://mirror.nbtelecom.com.br/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
```

Descompactar
```bash
sudo tar -xvzf apache-maven-3.6.3-bin.tar.gz
```

Alterar nome diretorio
```bash
sudo mv apache-maven-3.6.3 maven
```
Após a instalação, é necessário que as variáveis de ambiente sejam configuradas

Abrir o arquivo
```bash
sudo nano /etc/profile.d/maven.sh
```

Adicionar as seguites linhas e salvar as alterações:
```bash
export M2_HOME=/opt/ferramentas/maven
export PATH=${M2_HOME}/bin:${PATH}
```

Para ativar as configurações, execute os seguintes comandos
```bash
sudo chmod +x /etc/profile.d/maven.sh
source /etc/profile.d/maven.sh
```

Verificar versão instalada
```bash
mvn --version
```

### IntelliJ
Usando o IntelliJ, é necessário importar a pasta `{dir-projeto}/backend`. Durante a importação, selecione a opção que permite que o download das dependências seja feita de forma automática.

How Access: http://homologacao.sistemas.sema.ap.gov.br/configurador/#/login
