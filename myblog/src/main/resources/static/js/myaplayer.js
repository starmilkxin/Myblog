
const ap = new APlayer({
container: document.getElementById('aplayer'),
fixed: true,
autoplay: false,
listFolded: true,
theme: '#9bfff7',
loop: 'all',
order: 'random',
preload: 'auto',
volume: 0.5,
mutex: true,
audio: [
    {
        name: 'The First Snowflakes',
        artist: 'Richard Clayderman',
        url: '/music/Richard Clayderman-初雪（The First Snowflakes）.mp3',
        cover: '/music/cover/music_cover7.jpg',
        theme: 'rgba(206,253,243,0.47)'
    },
    {
        name: 'Snowdream',
        artist: 'Bandari',
        url: '/music/Snowdream.flac',
        cover: '/music/cover/music_cover4.jpg',
        theme: '#8df5e4'
    },
    {
        name: 'Nature\'s Path',
        artist: 'Dan Gibson\'s Solitudes',
        url: '/music/Dan Gibson\'s Solitudes - Nature\'s Path.mp3',
        cover: '/music/cover/music_cover1.jpg',
        theme: '#ef8a25'
    },
    {
        name: 'Days - Nuit Silencieuse.mp3',
        artist: 'Days',
        url: '/music/Days - Nuit Silencieuse.mp3',
        cover: '/music/cover/music_cover13.png',
        theme: '#ca6afd'
    },
    {
        name: 'Moonglow',
        artist: 'Bandari',
        url: '/music/Moonglow.flac',
        cover: '/music/cover/music_cover5.jpg',
        theme: '#1cecca'
    },
    {
        name: 'Endlesshorizon',
        artist: 'Bandari',
        url: '/music/Endlesshorizon.mp3',
        cover: '/music/cover/music_cover8.jpg',
        theme: '#8df5e4'
    },
    {
        name: 'One Day In Spring',
        artist: 'Bandari',
        url: '/music/One Day In Spring.flac',
        cover: '/music/cover/music_cover11.jpg',
        theme: '#34f63c'
    },
    {
        name: 'Liberstraum',
        artist: 'Richard Clayderman',
        url: '/music/Richard Clayderman - Liberstraum.mp3',
        cover: '/music/cover/music_cover6.png',
        theme: '#a05bf3'
    },
    {
        name: '思乡曲',
        artist: 'Richard Clayderman',
        url: '/music/Richard Clayderman - 思乡曲.flac',
        cover: '/music/cover/music_cover2.jpg',
        theme: '#d6b2f6'
    },
    {
        name: '水边的阿狄丽娜',
        artist: 'Richard Clayderman',
        url: '/music/Richard Clayderman - 水边的阿狄丽娜.flac',
        cover: '/music/cover/music_cover9.jpg',
        theme: 'rgba(164,79,236,0.85)'
    },
    {
        name: '爱的纪念',
        artist: 'Richard Clayderman',
        url: '/music/Richard Clayderman - 爱的纪念.flac',
        cover: '/music/cover/music_cover10.jpg',
        theme: '#ee599e'
    },
    {
        name: '雨的印记',
        artist: 'Yiruma',
        url: '/music/Yiruma - 雨的印记.mp3',
        cover: '/music/cover/music_cover12.jpg',
        theme: '#46bef8'
    },
    {
        name: '月明かり',
        artist: '本田宗,tororo',
        url: '/music/本田宗,tororo - 月明かり.mp3',
        cover: '/music/cover/music_cover3.jpg',
        theme: '#ee78df'
    }
    ]
});
