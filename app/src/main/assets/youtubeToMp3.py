from googleapiclient.discovery import build
from pytube import YouTube
import os
from pathlib import Path
#PART GET URL
youtube = build('youtube', 'v3', developerKey='AIzaSyD-JFJ1hIzgbEuz5KpcTMNCdizHp_8Zhss')

request = youtube.search().list(
    q='nelick', # Termes de recherche
    part='snippet', # Utilisez 'snippet' ici
    maxResults=10 # Nombre de résultats par requête
)

response = request.execute()
listVideo = []
print("-------------------------------FIRST STEP: GET URL-------------------------\n")
for item in response['items']:
    if item['id']['kind'] == 'youtube#video':
        video_id = item['id']['videoId']
        video_url = f'https://www.youtube.com/watch?v={video_id}'
        listVideo.append(video_url)
        print(video_url)
print("---------------------------------DONE---------------------------------------")
print("\n----------------------SECONDE STEP: DOWNLOAD FIRST SONG-----------------\n")
#PART DOWNLOAD
#test sumple => YouTube(listVideo[0]).streams.first().download()
yt = listVideo[1]
video = YouTube(yt).streams.filter(only_audio=True).first()

out_file = video.download(output_path='C:\\Users\\shain\\Documents\\S7\\MobileDevice')
base, ext = os.path.splitext(out_file)
new_file = Path(f'{base}.mp3')
os.rename(out_file, new_file)

#Check success of download
if new_file.exists():
     print(f'{YouTube(yt).title} has been successfully downloaded.')
else:
    print(f'ERROR: {YouTube(yt).title}could not be downloaded!')

print("---------------------------------DONE---------------------------------------")
