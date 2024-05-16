# -*- coding: utf-8 -*-
import json
import matplotlib.pyplot as plt

def read_json(file):
    partidas = []
    with open('games2.json', encoding='utf-8') as f:
        datos = json.load(f)
        partidas = datos['games']
    return partidas

def view_games_by_country(partidas):
    paises = {}
    aciertos = {}
    ratio={}
    for partida in partidas:
        if partida['selected']['country'] in paises:
            paises[partida['selected']['country']] += 1
            if partida['selected']['country'] not in aciertos and partida['win'] == True:
                aciertos[partida['selected']['country']] = 1
            elif partida['win'] == True:
                aciertos[partida['selected']['country']] += 1
        else:
            paises[partida['selected']['country']] = 1
            if partida['selected']['country'] not in aciertos and partida['win'] == True:
                aciertos[partida['selected']['country']] = 1
    for pais in paises.keys():
        ratio[pais] = aciertos[pais]/paises[pais]           
    print(ratio)
    paises_sorted = dict(sorted(paises.items(), key=lambda item: item[1], reverse=True))
    aciertos_sorted = {key: aciertos.get(key, 0) for key in paises_sorted.keys()}  
    plt.bar(paises_sorted.keys(), paises_sorted.values(), color='b', label='Partidas por país')
    plt.bar(aciertos_sorted.keys(), aciertos_sorted.values(), color='g', label='Aciertos por país')
    plt.xlabel('Países')
    plt.ylabel('Partidas')
    plt.title('Partidas y Aciertos según el país del equipo')
    plt.legend()
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.show()

def view_games_by_country_in_first_division(partidas):
    paises = {}
    aciertos = {}
    for partida in partidas:
        if partida['selected']['country'] in paises and partida['selected']['category'] == 1:
            paises[partida['selected']['country']] += 1
            if partida['selected']['country'] not in aciertos  and partida['win'] == True:
                aciertos[partida['selected']['country']] = 1
            elif partida['win'] == True and partida['selected']['category'] == 1:
                aciertos[partida['selected']['country']] += 1
        elif partida['selected']['category'] == 1:
            paises[partida['selected']['country']] = 1
            if partida['selected']['country'] not in aciertos and partida['selected']['category'] == 1 and partida['win'] == True:
                aciertos[partida['selected']['country']] = 1
               
    paises_sorted = dict(sorted(paises.items(), key=lambda item: item[1], reverse=True))
    aciertos_sorted = {key: aciertos.get(key, 0) for key in paises_sorted.keys()}  
    plt.bar(paises_sorted.keys(), paises_sorted.values(), color='b', label='Partidas por país')
    plt.bar(aciertos_sorted.keys(), aciertos_sorted.values(), color='g', label='Aciertos por país')
    plt.xlabel('Países')
    plt.ylabel('Partidas')
    plt.title('Partidas y Aciertos por País')
    plt.legend()
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.show()

def second_division_success_rate(partidas):
    success=0
    total=0
    for partida in partidas:
        if partida['selected']['category'] == 2:
            total += 1
            if partida['win'] == True:
                success += 1
    print('Tasa de aciertos en segunda división: ', success/total)

def success_rate_by_genre(partidas):
    m=0
    f = 0
    total_m=0
    total_f=0
    for partida in partidas:
        if partida['selected']['genre'] == 'M':
            total_m += 1
            if partida['win'] == True:
                m+=1
        else:
            total_f += 1
            if partida['win'] == True:
                f+=1
    
    m_rate = m / total_m if total_m > 0 else 0
    f_rate = f / total_f if total_f > 0 else 0
    fig, axs = plt.subplots(1, 2, figsize=(12, 6))
    axs[0].pie([m_rate, 1 - m_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[0].set_title('Partidas totales con escudos masculinos: {}'.format(total_m))
    axs[1].pie([f_rate, 1 - f_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[1].set_title('Partidas totales con escudos femeninos: {}'.format(total_f))
    plt.tight_layout()
    plt.show()

def success_rate_if_year(partidas):
    y=0
    ny = 0
    total_y=0
    total_ny=0
    for partida in partidas:
        if partida['selected']['hasYear']:
            total_y += 1
            if partida['win'] == True:
                y+=1
        else:
            total_ny += 1
            if partida['win'] == True:
                ny+=1
    
    y_rate = y / total_y if total_y > 0 else 0
    ny_rate = ny / total_ny if total_ny > 0 else 0
    fig, axs = plt.subplots(1, 2, figsize=(12, 6))
    axs[0].pie([y_rate, 1 - y_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[0].set_title('Partidas totales con escudos con año: {}'.format(total_y))
    axs[1].pie([ny_rate, 1 - ny_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[1].set_title('Partidas totales con escudos sin año: {}'.format(total_ny))
    plt.tight_layout()
    plt.show()

def success_rate_if_initials(partidas):
    i=0
    ni = 0
    total_i=0
    total_ni=0
    for partida in partidas:
        if partida['selected']['hasInitials']:
            total_i += 1
            if partida['win'] == True:
                i+=1
        else:
            total_ni += 1
            if partida['win'] == True:
                ni+=1
    
    i_rate = i / total_i if total_i > 0 else 0
    ni_rate = ni / total_ni if total_ni > 0 else 0
    fig, axs = plt.subplots(1, 2, figsize=(12, 6))
    axs[0].pie([i_rate, 1 - i_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[0].set_title('Partidas totales con escudos que incluyen las iniciales: {}'.format(total_i))
    axs[1].pie([ni_rate, 1 - ni_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[1].set_title('Partidas totales con escudos que no incluyen las iniciales: {}'.format(total_ni))
    plt.tight_layout()
    plt.show()

def success_rate_if_name(partidas):
    n=0
    nn = 0
    total_n=0
    total_nn=0
    for partida in partidas:
        if partida['selected']['hasName']:
            total_n += 1
            if partida['win'] == True:
                n+=1
        else:
            total_nn += 1
            if partida['win'] == True:
                nn+=1
    
    n_rate = n / total_n if total_n > 0 else 0
    nn_rate = nn / total_nn if total_nn > 0 else 0
    fig, axs = plt.subplots(1, 2, figsize=(12, 6))
    axs[0].pie([n_rate, 1 - n_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[0].set_title('Partidas totales con escudos que incluyen el nombre: {}'.format(total_n))
    axs[1].pie([nn_rate, 1 - nn_rate], labels=['Victorias', 'Derrotas'], autopct='%1.1f%%')
    axs[1].set_title('Partidas totales con escudos que no incluyen el nombre: {}'.format(total_nn))
    plt.tight_layout()
    plt.show()

def get_most_played_clubs(partidas):
    res ={}
    for partida in partidas:
        club = partida['selected']['name']
        if club in res:
            res[club] += 1
        else:
            res[club] = 1
    
    sorted_res = sorted(res.items(), key=lambda x: x[1], reverse=True)
    top_10_clubs = sorted_res[:10]
    clubs = [club[0] for club in top_10_clubs]
    appearances = [club[1] for club in top_10_clubs]
    plt.figure(figsize=(10, 6))
    plt.bar(clubs, appearances)
    plt.xlabel('Equipos')
    plt.ylabel('Numero de apariciones en partidas')
    plt.title('Top 10 de equipos con más apariciones en partidas')
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.show()
    return res
    
def get_most_wins_clubs(partidas):
    res ={}
    for partida in partidas:
        if partida['win']:
            club = partida['selected']['name']
            if club in res:
                res[club] += 1
            else:
                res[club] = 1
    
    sorted_res = sorted(res.items(), key=lambda x: x[1], reverse=True)
    top_10_clubs = sorted_res[:10]
    clubs = [club[0] for club in top_10_clubs]
    appearances = [club[1] for club in top_10_clubs]
    plt.figure(figsize=(10, 6))
    plt.bar(clubs, appearances)
    plt.xlabel('Equipos')
    plt.ylabel('Numero de victorias por club')
    plt.title('Top 10 de equipos que más veces han sido adiviniaos en partidas')
    plt.xticks(rotation=45, ha='right')
    plt.tight_layout()
    plt.show()
    return res

def get_teams_with_100accuracy_with_5_or_more_apperances(partidas):
    total = get_most_played_clubs(partidas)
    wins = get_most_wins_clubs(partidas)
    total_teams = {team: count for team, count in total.items() if count >= 5}
    full_accuracy_teams = {team for team in total_teams if team in wins and wins[team] == total_teams[team]}
    print(full_accuracy_teams)

def get_teams_with0accuracy(partidas):
    res ={}
    for partida in partidas:
        if partida['win']:
            club = partida['selected']['name']
            if club in res:
                res[club] += 1
            else:
                res[club] = 1
        else:
            if club not in res:
                res[club] = 0
    no_wins_teams = {team for team in res if res[team] == 0}
    print(no_wins_teams)

def get_least_successful_teams(partidas):
    apariciones = {}
    aciertos = {}
    for partida in partidas:
        club = partida['selected']['name']
        if club in apariciones:
            apariciones[club] += 1
            if partida['win'] and club in aciertos:
                aciertos[club] += 1
            else:
                aciertos[club] = 0
        else:
            apariciones[club] = 1
            aciertos[club] = 1 if partida['win'] else 0
    
    print(aciertos)
    ratio = {club: aciertos[club]/apariciones[club] for club in apariciones}
    top_10_clubs = {club: ratio[club] for club in ratio if ratio[club] == 0.0}
    print(top_10_clubs)
    print(len(top_10_clubs))

if __name__ == "__main__":
    partidas = read_json('games2.json')
    print(len(partidas))
    #view_games_by_country(partidas)
    #view_games_by_country_in_first_division(partidas)
    #second_division_success_rate(partidas)
    #success_rate_by_genre(partidas)
    #success_rate_if_year(partidas)
    #success_rate_if_initials(partidas)
    #success_rate_if_name(partidas)
    #get_most_played_clubs(partidas)
    #get_most_wins_clubs(partidas)
    #get_teams_with_100accuracy_with_5_or_more_apperances(partidas)
    #get_teams_with0accuracy(partidas)
    get_least_successful_teams(partidas)
