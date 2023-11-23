from googleapiclient.discovery import build
from pytube import YouTube
import os
from pathlib import Path
import logging
import json
#PART GET URL
def getMusics(title):
    youtube = build('youtube', 'v3', developerKey='AIzaSyD-JFJ1hIzgbEuz5KpcTMNCdizHp_8Zhss')

    request = youtube.search().list(
        q=title, # Termes de recherche
        part='snippet', # Utilisez 'snippet' ici
        maxResults=3 # Nombre de résultats par requête
    )

    response = request.execute()

    processed_videos = []
    for item in response['items']:
        if item['id']['kind'] == 'youtube#video':
            video_id = item['id']['videoId']
            video_data = {
                'video_id': video_id,
                'video_url': f'https://www.youtube.com/watch?v={video_id}',
                'thumbnail_url': item['snippet']['thumbnails']['high']['url'],
                'title': item['snippet']['title'],
                'channel_title': item['snippet']['channelTitle']
            }
            processed_videos.append(video_data)

    ####TEST####
    # return video_url
    ###########

    return json.dumps(processed_videos)

def downloadMusics(Url, outp_path):
    #test sumple => YouTube(listVideo[0]).streams.first().download()

    #return yt
    video = YouTube(Url).streams.filter(only_audio=True).first()
    Title = YouTube(Url).title
    out_file = video.download(output_path=outp_path)
    base, ext = os.path.splitext(out_file)
    new_file = Path(f'{base}.mp3')
    os.rename(out_file, new_file)

    #Check success of download
    if new_file.exists():
        print(f'{Title} has been successfully downloaded.')
    else:
        print(f'ERROR: {Title}could not be downloaded!')

def create_file(path, filename, content):
    full_path = os.path.join(path, filename)
    with open(full_path, 'w') as file:
        file.write(content)
    return f"File saved at {full_path}"


    #print("-------------------------------FIRST STEP: GET URL-------------------------\n")
    #for item in response['items']:
        #if item['id']['kind'] == 'youtube#video':
            #video_id = item['id']['videoId']
            #video_url = f'https://www.youtube.com/watch?v={video_id}'
            #listVideo.append(video_url)
            #print(video_url)
    #print("---------------------------------DONE---------------------------------------")
    #print("\n----------------------SECONDE STEP: DOWNLOAD FIRST SONG-----------------\n")
    #PART DOWNLOAD
    #test sumple => YouTube(listVideo[0]).streams.first().download()
    #yt = listVideo[1]
    #return yt
    #video = YouTube(yt).streams.filter(only_audio=True).first()

    #out_file = video.download(output_path='C:\\Users\\shain\\Documents\\S7\\MobileDevice')
    #base, ext = os.path.splitext(out_file)
    #new_file = Path(f'{base}.mp3')
    #os.rename(out_file, new_file)

    #Check success of download
    #if new_file.exists():
        #print(f'{YouTube(yt).title} has been successfully downloaded.')
    #else:
        #print(f'ERROR: {YouTube(yt).title}could not be downloaded!')

    #print("---------------------------------DONE---------------------------------------")
    