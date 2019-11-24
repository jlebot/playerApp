#!/bin/bash

print_usage() {
    echo "${1} <plugin> <action>"
    echo
    echo "plugin: all server front"
    echo "action: up down status"
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
	all)
	    case "${ARG_ACTION}" in
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
	server)
	    case "${ARG_ACTION}" in
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
    printf "[  ${tput_green}ON${tput_reset}  ]\n"
    logging "Step is ON"
    logging "---"
}

print_status_end_OFF() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[  ${tput_red}OFF${tput_reset} ]\n"
    logging "Step is OFF"
    logging "---"
}

print_status_end_OK() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[  ${tput_green}OK${tput_reset}  ]\n"
    logging "Step is OK"
    logging "---"
}

print_status_end_ERROR() {
    G_STATUS_END_TIME=$(date +%s)
    print_status_compute_time
    printf "[${tput_red}ERROR!${tput_reset}]\n"
    logging "Step is ERROR"
    logging "---"
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

front_getStatus() {
    if lsof -Pi :4200 -sTCP:LISTEN -t >/dev/null;
    then
	logging "front is up"
	return 1
    fi
    logging "front is down"
    return 0
}

all_getStatus() {
    server_getStatus
	front_getStatus
}

server_getUp() {
    print_status_begin "Starting the server"

    if server_getStatus
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

front_getUp() {
    print_status_begin "Starting the front"

    if front_getStatus
    then
	logging "front already started"
	print_status_end_OK
	return 0
    fi

    if ! (cd "frontend" && ng serve)
	then
	print_status_end_ERROR
	return 1
    fi

    print_status_end_DONE
    return 0
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
		echo "The program ended with errors."
		print_status_begin "${tput_red}The program ended with errors.${tput_reset}"
		print_status_end_ERROR
    fi
    exit "${RES}"
)

exit ${PIPESTATUS[0]}
### END OF THE PROGRAM ###
