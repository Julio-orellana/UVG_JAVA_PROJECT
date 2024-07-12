import datetime as dt
import pandas as pd
from time import sleep
import tkinter as tk
import os
from tkinter import messagebox as mb
import threading

# Get the path of the current directory
def getPath(directory=None):
    if not directory:
        return os.path.dirname(os.path.abspath(__file__))
    return f"{os.path.dirname(os.path.abspath(__file__))}/{directory}"

# Create the interface class to handle the GUI
class Interface:
    def __init__(self):
        # Attributes
        self.root = tk.Tk()
        self.root.title("Gestión de tiempo")
        self.root.geometry("400x200")
        self.root.resizable(True, True)
        self.root.configure(bg="light blue")
        self.label = tk.Label(self.root, text="Gestión de tiempo", bg="light blue", font=("Arial", 20))
        self.label.pack(pady=10)
        self.button = tk.Button(self.root, text="Iniciar", bg="light green", font=("Arial", 15), command=self.start_timer)
        self.button.pack(pady=10)
        self.root.mainloop()
        
    def start_timer(self, timeInSeconds):
        # Error handling with try-except block
        try:
            # Create an instance of the Timer class and start the timer
            temporizador = Timer(timeInSeconds)
            # Initiate the thread to start the timer and the counter
            t = threading.Thread(target=temporizador.start_counter)
            t.start()
            
            # Simulate the pause and continue of the timer
            sleep(2)
            temporizador.pause_counter()
            print("Temporizador en pausa")
            sleep(4)
            temporizador.continue_counter()
            print("Temporizador continuado")

            t.join()
            start_time, diff_time, end_time = temporizador.stop_counter()
            paused_time = temporizador.paused_time_total
            paused_time_str = str(paused_time).split(".")[0]
            df = temporizador.create_DataFrame(start_time, diff_time, end_time, paused_time_str)
            temporizador.export_data_to_excel(df)
        except Exception as e:
            print(e)

# Create the FileHandling class to handle the data
class FileHandling:
    # Constructor
    def __init__(self):
        # Attributes
        self.AutomatizationDirectory = getPath()
        self.counter_start_time = None
        self.file = "gestion_de_tiempo.xlsx"
        if os.path.exists(f"{self.AutomatizationDirectory}/{self.file}"):
            self.data = pd.read_excel(f"{self.AutomatizationDirectory}/{self.file}")
        else:
            self.data = pd.DataFrame(columns=["Hora de inicio", "Diferencia de tiempo", "Hora de finalización", "Tiempo pausado"])

    # Methods
    def getData(self):
        return self.data
        
    def parseData(self):
        df = self.getData()
        start_time = df["Hora de inicio"].to_dict()
        time_diff = df["Diferencia de tiempo"].to_dict()
        end_time = df["Hora de finalización"].to_dict()
        paused_time = df["Tiempo pausado"].to_dict()
        data = {"Hora de inicio": start_time, "Diferencia de tiempo": time_diff, "Hora de finalización": end_time, "Tiempo pausado": paused_time}
        return data

    def create_DataFrame(self, start_time, time_diff, end_time, paused_time):
        data = self.parseData()
        for i in range(len(data["Hora de inicio"].keys()) + 1):
            if i not in data["Hora de inicio"].keys():
                data["Hora de inicio"][i] = start_time
                data["Diferencia de tiempo"][i] = time_diff
                data["Hora de finalización"][i] = end_time
                data["Tiempo pausado"][i] = paused_time
                break
        return pd.DataFrame(data)

    def export_data_to_excel(self, df: pd.DataFrame):
        if df.empty:
            print("No se ha podido exportar la información a un archivo Excel")
        df.to_excel(f"{self.AutomatizationDirectory}/gestion_de_tiempo.xlsx", index=False)
        print("Información exportada a el archivo Excel")

# ********************************************************************************************************************
# Create the Timer class to handle the timer
class Timer(FileHandling): # Inherit from the FileHandling class
    # Constructor
    def __init__(self, secs):
        # Attributes
        self.secs = secs
        self.paused = False
        self.paused_time_total = dt.timedelta()
        # Call the constructor of the parent class
        super().__init__()
        
    # Methods
    def start_counter(self):
        self.counter_start_time = dt.datetime.now()
        while self.secs:
            if not self.paused:
                mins, secs = divmod(self.secs, 60)
                timer = '{:02d}:{:02d}'.format(mins, secs)
                print(timer, end='\r')
                sleep(1)
                self.secs -= 1

            pause_start = dt.datetime.now()
            while self.paused:
                sleep(0.1)
            pause_end = dt.datetime.now()
            self.paused_time_total += (pause_end - pause_start)
        return self.stop_counter()

    def pause_counter(self):
        self.paused = True

    def continue_counter(self):
        self.paused = False

    def stop_counter(self):
        end_time = dt.datetime.now()
        diff_time = end_time - self.counter_start_time - self.paused_time_total
        start_time_str = self.counter_start_time.strftime("%d/%m/%Y, %H:%M:%S")
        end_time_str = end_time.strftime("%d/%m/%Y, %H:%M:%S")
        diff_time_str = str(diff_time).split(".")[0]
        return start_time_str, diff_time_str, end_time_str
    
# # ********************************************************************************************************************
# # Error handling with try-except block
# try:
#     # Create an instance of the Timer class and start the timer
#     temporizador = Timer(5)
#     # Initiate the thread to start the timer and the counter
#     t = threading.Thread(target=temporizador.start_counter)
#     t.start()
    
#     # Simulate the pause and continue of the timer
#     sleep(2)
#     temporizador.pause_counter()
#     print("Temporizador en pausa")
#     sleep(4)
#     temporizador.continue_counter()
#     print("Temporizador continuado")

#     t.join()
#     start_time, diff_time, end_time = temporizador.stop_counter()
#     paused_time = temporizador.paused_time_total
#     paused_time_str = str(paused_time).split(".")[0]
#     df = temporizador.create_DataFrame(start_time, diff_time, end_time, paused_time_str)
#     temporizador.export_data_to_excel(df)
# except Exception as e:
#     print(e)