// develop by @koyaja Jacques KOMACLO
/**
 * @Author @koyaja Jacques KOMACLO
 * @Description Api pour lire les voix naturelle avec SOFT MEDIA
 **/

var voiceStaticActif = true;
var audio = new Audio();
// dossier dans lequel se trouve les voies
var audioLocation = '../voice/';
var textTicket = 'ticket.wav';
var textTicket = 'ticket.wav';
var timeoutVal = 10;
/**
 * EX A001, caisse 1
 * @param numberValur exemple Numero 001
 * @param letterValur ex A
 * @param postValur  1
 * @constructor
 */
ReadVoice = function (numberValur, letterValur, postValur) {
    var p1 = audioLocation + text1;
    var p1 = audioLocation + text1;
    setTimeout(playWav(p1), timeoutVal)
}

playWav = function (val) {
    audio.src = val;
    audio.play();
}

