import datetime as dt
import pandas as pd
from time import sleep
# import tkinter as tk
# from tkinter import messagebox as mb

# mins = float(input("Ingrese los minutos: "))
# secs = int(mins * 60)

class Interface:
    pass

class Timer:
    def __init__(self, secs):
        self.secs = secs

    def start_counter(self):
        while not self.pause_counter():
            start_time = dt.datetime.now()
            while self.secs:
                mins, secs = divmod(self.secs, 60)
                timer = '{:02d}:{:02d}'.format(mins, secs)
                print(timer, end='\r')
                sleep(1)
                self.secs -= 1
            return start_time

    def pause_counter(self, paused=False):
        return True if paused else False

    def stop_counter(self, start_time=None):
        print("Se acabó el tiempo")
        end_time = dt.datetime.now()
        diff_time = end_time - start_time
        diff_time = str(diff_time).split(".")[0]
        return end_time, diff_time
        # print("Tiempo total: ", end_time - start_time)

    def export_data_to_excel(self, start, diff_time, end):
        start = str(start)
        diff_time = str(diff_time)
        end = str(end)
        # data = pd.read_excel("gestion_de_tiempo.xlsx")
        newData = {
            "Hora de inicio": [start],
            "Diferencia de tiempo": [diff_time],
            "Hora de finalización": [end]
        }
        df = pd.DataFrame(newData)
        df.to_excel("gestion_de_tiempo.xlsx", index=False)

if "__name__" == "__main__":
    try:
        temporizador = Timer(3)
        start_time = temporizador.start_counter()
        end_time, diff_time = temporizador.stop_counter(start_time)
        temporizador.export_data_to_excel(start_time.strftime("%d/%m/%Y, %H:%M:%S"), diff_time, end_time.strftime("%d/%m/%Y, %H:%M:%S"))
    except temporizador.pause_counter():
        print("\nSe ha detenido el temporizador")
    except Exception as e:
        print(e)

    quit()