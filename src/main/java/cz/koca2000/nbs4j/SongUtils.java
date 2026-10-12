package cz.koca2000.nbs4j;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class SongUtils {

    private SongUtils() {
    }

    public static int findTempoChangerInstrumentIndex(@NotNull List<CustomInstrument> customInstruments) {
        for (int i = 0; i < customInstruments.size(); i++) {
            if (CustomInstrument.TEMPO_CHANGER_INSTRUMENT_NAME.equals(customInstruments.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public static int findSoundStopperInstrumentIndex(@NotNull List<CustomInstrument> customInstruments) {
        for (int i = 0; i < customInstruments.size(); i++) {
            if (CustomInstrument.SOUND_STOPPER_INSTRUMENT_NAME.equals(customInstruments.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public static int getSoundStopperStartLayer(@NotNull Note note) {
        return note.getPitch();
    }

    public static int getSoundStopperEndLayer(@NotNull Note note) {
        int panOpenNbs = (note.getPanning() + 100) & 0xFF;
        int velOpenNbs = note.getVolume() & 0xFF;

        int pan = (panOpenNbs + 156) % 256;
        int vel = (velOpenNbs + 156) % 256;
        return pan + vel * 256;
    }

    public static Note buildSoundStopperNote(int instrumentId, boolean isCustomInstrument, int startLayer, int endLayer) {
        int vel = endLayer / 256;
        int pan = endLayer - (vel * 256);
        int openNbsVel = (vel + 100) % 256;
        int openNbsPan = (pan + 100) % 256;

        return Note.builder()
                .instrument(instrumentId, isCustomInstrument)
                .rawVolume((byte) openNbsVel)
                .rawPanning(openNbsPan - 100)
                .pitch(startLayer)
                .build();
    }
}
