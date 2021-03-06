#!/bin/bash

print_usage() {
    echo "${1} <plugin> <action>"
    echo
    echo "plugin: server front all"
    echo "action: install up down status"
    echo
}

############
### MAIN ###
############

run() {
    PRG=${1}
    ARG_PLUGIN=${2}
    ARG_ACTION=${3}
    shift
    shift
    shift
	shift

    case "${ARG_PLUGIN}" in
	server)
	    case "${ARG_ACTION}" in
		"install")
		    if ! server_getInstall
			then
			return 1
		    fi
		    ;;
		"up")
		    if ! server_getUp
			then
			return 1
		    fi
		    ;;
		"down")
		    if ! server_getDown
			then
			return 1
		    fi
		    ;;
		"status")
		    if ! server_getStatus
		    then
			return 1
		    fi
		    ;;
		*)
		    print_usage "${PRG}"
		    return 1
	    esac
	    ;;
	front)
	    case "${ARG_ACTION}" in
		"install")
		    if ! front_getInstall
			then
			return 1
		    fi
		    ;;
		"up")
		    if ! front_getUp
			then
			return 1
		    fi
		    ;;
		"down")
		    if ! front_getDown
			then
			return 1
		    fi
		    ;;
		"status")
		    if ! front_getStatus
		    then
			return 1
		    fi
		    ;;
		*)
		    print_usage "${PRG}"
		    return 1
	    esac
	    ;;
	all)
	    case "${ARG_ACTION}" in
		"install")
		    if ! all_getInstall
			then
			return 1
		    fi
		    ;;
		"up")
		    if ! all_getUp
			then
			return 1
		    fi
		    ;;
		"down")
		    if ! all_getDown
			then
			return 1
		    fi
		    ;;
		"status")
		    if ! all_getStatus
		    then
			return 1
		    fi
		    ;;
		*)
		    print_usage "${PRG}"
		    return 1
	    esac
	    ;;
	*)
	    print_usage "${PRG}"
	    return 1
    esac
}

if test $# -lt 2
then
    print_usage "$0"
    exit 1
fi

# tput global values
tput_reset="$(tput sgr0)"
tput_red="$(tput bold ; tput setaf 1)"
tput_green="$(tput bold ; tput setaf 2)"

logging() {
    CDATE=$(date +%Y-%m-%d-%H:%M:%S)
    echo "${CDATE} ${1}"
}

#########################
### DISPLAY FUNCTIONS ###
#########################

secToStr() {
    TR_SEC=$(expr "${1}" % "60")
    TR_MIN=$(expr "${1}" / "60")
    TR_MIN=$(expr "${TR_MIN}" % "60")
    TR_HR=$(expr "${1}" / "3600")

    # formating
    test "${TR_SEC}" -lt 10 && TR_SEC="0${TR_SEC}"
    test "${TR_MIN}" -lt 10 && TR_MIN="0${TR_MIN}"

    echo "${TR_HR}:${TR_MIN}:${TR_SEC}"
}

print_status_compute_time() {
    DELTA_TIME=$(expr ${G_STATUS_END_TIME} - ${G_STATUS_BEGIN_TIME})
    DELTA_TIME_STR=$(secToStr "${DELTA_TIME}")
    printf "%-100s %s\n" "${G_STATUS_BEGIN_STEP}" "${DELTA_TIME_STR}"
}

print_status_begin() {
    G_STATUS_BEGIN_TIME=$(date +%s)
    G_STATUS_BEGIN_STEP="${1}"
    logging "Step ${1}"
}

print_status_end_ON() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[${tput_green}ON${tput_reset}]\n"
    logging "Step is ON"
}

print_status_end_OFF() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[${tput_red}OFF${tput_reset}]\n"
    logging "Step is OFF"
}

print_status_end_OK() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[${tput_green}OK${tput_reset}]\n"
    logging "Step is OK"
}

print_status_end_DONE() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[${tput_green}DONE${tput_reset}]\n"
    logging "Step is DONE"
}

print_status_end_ERROR() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[${tput_red}ERROR!${tput_reset}]\n"
    logging "Step is ERROR"
}

server_getStatus() {
    if lsof -Pi :8080 -sTCP:LISTEN -t >/dev/null;
    then
	logging "server is up"
	return 1
    fi
    logging "server is down"
    return 0
}

server_getInstall() {
    print_status_begin "Building app server"

    if ! ./gradlew build
	then
	print_status_end_ERROR
	return 1
    fi

    print_status_end_DONE
    return 0
}

server_getUp() {
    print_status_begin "Starting the server"

    if ! server_getStatus
    then
	logging "server already started"
	print_status_end_OK
	return 0
    fi

    if ! ./gradlew run
	then
	print_status_end_ERROR
	return 1
    fi

    print_status_end_DONE
    return 0
}

server_getDown() {
    logging "Not implemented yet"
}

front_getStatus() {
    if lsof -Pi :4200 -sTCP:LISTEN -t >/dev/null;
    then
	logging "front is up"
	return 1
    fi
    logging "front is down"
    return 0
}

front_getInstall() {
    print_status_begin "Installing front components"

    if ! (cd "frontend/betclic-app" && npm install)
	then
	print_status_end_ERROR
	return 1
    fi

    print_status_end_DONE
    return 0
}

front_getUp() {
    print_status_begin "Starting the front"

    if ! front_getStatus
    then
	logging "front already started"
	print_status_end_OK
	return 0
    fi

    if ! (cd "frontend/betclic-app" && ng serve)
	then
	print_status_end_ERROR
	return 1
    fi

    print_status_end_DONE
    return 0
}

front_getDown() {
    logging "Not implemented yet"
}

all_getStatus() {
    server_getStatus
	front_getStatus
	return 0
}

all_getInstall() {
    #server_getInstall || return 1
	#front_getInstall  || return 1
	logging "Not implemented yet"
}

all_getUp() {
    #server_getUp || return 1
	#front_getUp  || return 1
	logging "Not implemented yet"
}

all_getDown() {
    #server_getDown || return 1
	#front_getDown  || return 1
	logging "Not implemented yet"
}

ARG_PLUGIN=${1}
ARG_ACTION=${2}

(
    run "${0}" "${ARG_PLUGIN}" "${ARG_ACTION}"
    RES=$?

    if test "${RES}" -eq 0
    then
		echo "${tput_green}The program ended successfully.${tput_reset}"
    else
		echo "${tput_red}The program ended with errors.${tput_reset}"
    fi
    exit "${RES}"
)

exit ${PIPESTATUS[0]}
### END OF THE PROGRAM ###
