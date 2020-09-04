import { TranslationMetaData } from './interfaces';

const translationMetaData: Map<string, TranslationMetaData> = new Map();
translationMetaData.set('SI_LK', {
  name: 'සිංහල',
  placeholder: 'මාතෘකාව සඳහා සිංහල පරිවර්තනය ඇතුලත් කරන්න',
  title: 'මාතෘකාව',
});
translationMetaData.set('TA_LK', {
  name: 'தமிழ்',
  placeholder: 'தலைப்புக்கு சிங்கள மொழிபெயர்ப்பை உள்ளிடவும்',
  title: 'தலைப்பு',
});

export default translationMetaData;
