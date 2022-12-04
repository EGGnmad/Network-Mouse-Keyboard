import tkinter
import socket

TCP_IP = '127.0.0.1' #change ip
TCP_PORT = 8888

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #TCP
sock.connect((TCP_IP, TCP_PORT))

def key_down(e):
    key = e.keysym
    release = False
    state = e.state

    data2 = [key,release,state]

    if(data2[0] == 'Escape' and data2[2] == 1):
        exit()
    if(data2[0] == 'Shift_L' or data2[0] == 'Shift_R'):
        key = 'Shift'
    if(data2[0] == 'Control_L' or data2[0] == 'Control_R'):
        key = 'Control'
    if(data2[0] == 'Return'):
        key = 'Enter'

    data = f"KEY,{key},{release},{state}"
    sock.send(bytes(data, 'utf-8'))


def key_release(e):
    key = e.keysym
    release = True
    state = e.state

    data2 = [key,release,state]
    if(data2[0] == 'Shift_L' or data2[0] == 'Shift_R'):
        key = 'Shift'
    if(data2[0] == 'Control_L' or data2[0] == 'Control_R'):
        key = 'Control'

    data = f"KEY,{key},{release},{state}"
    sock.send(bytes(data, 'utf-8'))


def mouse_over(e):
    x = e.x
    y = e.y

    data = f"MOUSE,{x},{y}"
    sock.send(bytes(data, 'utf-8'))

while True:

    root = tkinter.Tk()

    root.title("input")

    #BINDS
    root.bind("<KeyPress>", key_down)
    root.bind("<KeyRelease>", key_release)
    root.bind("<Motion>", mouse_over)

    root.mainloop()