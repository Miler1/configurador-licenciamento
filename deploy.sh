#!/bin/bash
# Número da versão
versao=$(git describe --tags)
data_atual=$(date +'%Y.%m.%d-%H:%M')

test_server_host="sysadmin@177.105.35.81"

test_server_passwd="L3m4f S3rv3r"

test_server_dist_folder="/var/spring/configurador-licenciamento"

test_dist_file_name="configuradorlicenciamento-0.0.1.jar"

### Verificação de status das operações
### $1 - status, $2 - successMessage, $3 - errorMessage
checkStatus() {

	if [ $1 -eq 0 ]; then
		echo -e "\n<-- $2"
	else
		echo -e "\n<-- $3"
		exit 1
	fi

}

build-frontend() {

	echo -e "\n--> Compilando frontend"

	cd frontend

	rm package-lock.json

	npm install

	npm run build

	checkStatus $? "Frontend compilado com sucesso!" "Erro ao compilar o Frontend! Exit: $?"

	cd ../
}

clean() {

	echo -e "\n--> Backend: mvn clean"

	cd backend

	mvn clean

	checkStatus $? "Backend: mvn clean terminado com sucesso!" "Erro realizar o comando mvn clean: $?"

	cd ../
}

compile() {

	echo -e "\n--> Backend: mvn compile"

	cd backend

	mvn compile #-Dmaven.test.skip=true

	checkStatus $? "Backend: mvn compile terminado com sucesso!" "Erro realizar o comando mvn compile: $?"

	cd ../
}

package() {

	echo -e "\n--> Backend: mvn package"

	cd backend

	mvn package #-Dmaven.test.skip=true

	checkStatus $? "Backend mvn package terminado com sucesso!" "Erro ao realizar o comando mvn package: $?"

	cd ../
}

deploy() {

	echo -e "\n--> Enviando nova versão da aplicação para o servidor: ${server_dist_folder}/${dist_file_name}"

	sshpass -p "$server_pass" scp backend/target/*.jar $server_ssh:$server_dist_folder/$dist_file_name

	checkStatus $? "Nova versão da aplicação enviada para o servidor com sucesso!" "Erro ao enviar nova versão da aplicação para o servidor! Exit: $?"

}

publish() {

	sshpass -p "$server_pass" ssh -T $server_ssh <<-SERVER

		echo -e "\n--> Reiniciando a aplicação"

		echo "$server_pass" | sudo -S systemctl restart configurador-licenciamento.service

	SERVER

}

### Rodando os scripts de acordo com o ambiente

function usage() {

	echo -e "Usage: ./deploy environment [options]"
	echo -e "Environments: java1test"

}

##### Main

for ((i = 2; $i <= $#; i = $i + 1)); do

	case ${!i} in
	*)
		usage
		exit 1
		;;

	esac

done

ambiente=$1

case $ambiente in

teste)

	echo -e "--> FAZENDO DEPLOY DA APLICAÇÃO NO AMBIENTE DE TESTES"
	echo -e "\n\n\n|------ ESTEJA NA VPN UFLA ------|\n\n\n"

	server_ssh=$test_server_host
	server_pass=$test_server_passwd
	server_dist_folder=$test_server_dist_folder
	dist_file_name=$test_dist_file_name

	build-frontend
	clean
	compile
	package
	deploy
	publish

	echo -e "\n--> DEPLOY DA APLICAÇÃO NO AMBIENTE DE TESTES CONCLUÍDO"
	;;

*)

	usage
	exit 1

	;;

esac

exit 0
