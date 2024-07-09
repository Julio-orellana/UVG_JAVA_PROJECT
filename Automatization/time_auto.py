import datetime as dt
import pandas as pd
from time import sleep
import tkinter as tk
import os
from tkinter import messagebox as mb

def getPath(directory=None):
    if not directory:
        return os.path.dirname(os.path.abspath(__file__))
    return f"{os.path.dirname(os.path.abspath(__file__))}/{directory}"

class Interface:
    ROOT = tk.Tk()
    pass

class FileHandling:
    def __init__(self):
        self.AutomatizationDirectory = getPath()
        self.counter_start_time = None
        self.file = "gestion_de_tiempo.xlsx"
        self.data = pd.read_excel(f"{self.AutomatizationDirectory}/{self.file}")

    def getData(self):
        return self.data
        
    def parseData(self):
        df = self.getData()
        start_time = df["Hora de inicio"].to_dict()
        time_diff = df["Diferencia de tiempo"].to_dict()
        end_time = df["Hora de finalización"].to_dict()
        data = {"Hora de inicio": start_time, "Diferencia de tiempo": time_diff, "Hora de finalización": end_time}
        return data
    
    def create_DataFrame(self, start_time, time_diff, end_time):
        data = self.parseData()
        for i in range(len(data["Hora de inicio"].keys()) + 1):
            if i not in data["Hora de inicio"].keys():
                data["Hora de inicio"][i] = start_time
                data["Diferencia de tiempo"][i] = time_diff
                data["Hora de finalización"][i] = end_time
                break
        return pd.DataFrame(data)

    def export_data_to_excel(self, df: pd.DataFrame):
        if df.empty:
            print("No se ha podido exportar la información a un archivo Excel")
            # return mb.showerror("Error", "No se ha podido exportar la información a un archivo Excel")
        else:
            sleep(1)
            df.to_excel(f"{self.AutomatizationDirectory}/gestion_de_tiempo.xlsx", index=False)
            print("Se ha exportado la información a un archivo Excel")

class Timer(FileHandling):
    def __init__(self, secs):
        self.secs = secs
        super().__init__()

    def start_counter(self):
        start_time = dt.datetime.now()
        self.counter_start_time = start_time
        while self.secs:
            mins, secs = divmod(self.secs, 60)
            timer = '{:02d}:{:02d}'.format(mins, secs)
            print(timer, end='\r')
            sleep(1)
            self.secs -= 1
        return self.stop_counter(start_time)

    def pause_counter(self, paused=False):
        start_time = dt.datetime.now()
        continue_counter = True
        while paused and continue_counter:
            action = None
            continue_counter = self.continue_counter(start_time)
            if action:
                paused_time = self.continue_counter(start_time, True)
                return paused_time

    def continue_counter(self, start_time: dt.datetime, continue_counter=False):
        if not continue_counter:
            end_time = dt.datetime.now()
            paused_time = end_time - start_time
            return paused_time
        return True

    def stop_counter(self, start_time: dt.datetime):
        print("Deteniendo temporizador...\n")
        end_time = dt.datetime.now()
        diff_time = end_time - start_time
        diff_time_str = str(diff_time).split(".")[0]
        return diff_time_str, end_time

# Run the code
try:
    temporizador = Timer(5)
    diff_time, end_time = temporizador.start_counter()
    start_time = temporizador.counter_start_time.strftime("%d/%m/%Y, %H:%M:%S")
    end_time_str = end_time.strftime("%d/%m/%Y, %H:%M:%S")
    df = temporizador.create_DataFrame(start_time, diff_time, end_time_str)
    temporizador.export_data_to_excel(df)
except Exception as e:
    print(e)