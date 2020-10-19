public class Intervals {
    public static String intervalConstruction(String[] args) {
        int noteDegree = 0;
        int notesCount = 8;
        int semitoneCount = 0;
        int codePoint = 0;
        String resultingNote = null;
        boolean ascending = true;
        String secondNote = null;

        if (!(args.length >= 2 && args.length <= 3)) {
            try {
                throw new Exception("Illegal number of elements in input array");
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }
        String firstNote = args[1].substring(0, 1);
        switch (args[0]) {
            case "m2":
                semitoneCount = 1;
                noteDegree = 2;
                break;
            case "M2":
                semitoneCount = 2;
                noteDegree = 2;
                break;
            case "m3":
                semitoneCount = 3;
                noteDegree = 3;
                break;
            case "M3":
                semitoneCount = 4;
                noteDegree = 3;
                break;
            case "P4":
                semitoneCount = 5;
                noteDegree = 4;
                break;
            case "P5":
                semitoneCount = 7;
                noteDegree = 5;
                break;
            case "m6":
                semitoneCount = 8;
                noteDegree = 6;
                break;
            case "M6":
                semitoneCount = 9;
                noteDegree = 6;
                break;
            case "m7":
                semitoneCount = 10;
                noteDegree = 7;
                break;
            case "M7":
                semitoneCount = 11;
                noteDegree = 7;
                break;
            case "P8":
                semitoneCount = 12;
                noteDegree = 8;
                break;
        }
        if (args.length == 3 && args[2].equals("dsc")) {
            ascending = false;
        }
        // note matrix creation
        String[][] notesMatrix = new String[notesCount][notesCount];

        // filling the note matrix
        for (int i = 0; i < notesCount; i++) {
            for (int j = 0; j < notesCount; j++) {
                if (i == 0 && j == 0) {
                    notesMatrix[i][j] = "C";
                    codePoint = "C".codePointAt(0);
                    continue;
                }
                notesMatrix[i][j] = Character.toString((char) (++codePoint));

                if (notesMatrix[i][j].equals("G")) {
                    codePoint = "A".codePointAt(0) - 1;
                }
            }
        }
        // find second note
        int counter = 0;
        if (ascending) {
            for (int i = 0; i < notesCount; i++) {
                for (int j = 0; j < notesCount; j++) {
                    counter++;
                    if (counter == noteDegree) {
                        secondNote = notesMatrix[i][j];
                        break;
                    }
                    if (notesMatrix[i][j].equals(firstNote)) {
                        counter = 1;
                    }
                }
            }
        } else {
            for (int i = notesCount - 1; i >= 0; i--) {
                for (int j = notesCount - 1; j >= 0; j--) {
                    counter++;
                    if (counter == noteDegree) {
                        secondNote = notesMatrix[i][j];
                        break;
                    }
                    if (notesMatrix[i][j].equals(firstNote)) {
                        counter = 1;
                    }
                }
            }
        }

        //semitones sum search
        String[] semitoneAndNoteArr = {"C", "2", "D", "2", "E", "1", "F", "2", "G", "2", "A", "2", "B", "1", "C",
                "2", "D", "2", "E", "1", "F", "2", "G", "2", "A", "2", "B", "1", "C"};
        int semitonesSum = 0;
        if (firstNote.equals(secondNote)) {
            semitonesSum = 12;

        } else {
            boolean meetingFistNote = false;
            if (ascending) {
                for (int i = 0; i < semitoneAndNoteArr.length; i++) {

                    if (i % 2 != 0) {
                        semitonesSum += Integer.parseInt(semitoneAndNoteArr[i]);
                    }
                    if (i % 2 == 0 && semitoneAndNoteArr[i].equals(firstNote)) {
                        semitonesSum = 0;
                        meetingFistNote = true;
                        continue;
                    }
                    if (semitoneAndNoteArr[i].equals(secondNote) && meetingFistNote) {
                        break;
                    }
                }
            } else {
                for (int i = semitoneAndNoteArr.length - 1; i >= 0; i--) {

                    if (i % 2 != 0) {
                        semitonesSum += Integer.parseInt(semitoneAndNoteArr[i]);
                    }
                    if (semitoneAndNoteArr[i].equals(firstNote)) {
                        semitonesSum = 0;
                        meetingFistNote = true;
                        continue;
                    }
                    if (semitoneAndNoteArr[i].equals(secondNote) && meetingFistNote) {
                        break;
                    }
                }
            }
        }
        //semitones sum correction
        if (args[1].length() == 2) {
            if (ascending) {
                if (args[1].charAt(1) == '#') {
                    semitonesSum--;
                } else {
                    semitonesSum++;
                }

            } else {
                if (args[1].charAt(1) == '#') {
                    semitonesSum++;
                } else {
                    semitonesSum--;
                }
            }
        }
        //building resultingNote
        int semitoneDifference = semitoneCount - semitonesSum;
        if (semitoneDifference == 0) {
            resultingNote = secondNote;
        } else {
            if (ascending) {
                if (semitoneDifference == 1) {
                    resultingNote = secondNote + "#";
                }
                if (semitoneDifference == 2) {
                    resultingNote = secondNote + "##";
                }
                if (semitoneDifference == -1) {
                    resultingNote = secondNote + "b";
                }
                if (semitoneDifference == -2) {
                    resultingNote = secondNote + "bb";
                }
            } else {
                if (semitoneDifference == 1) {
                    resultingNote = secondNote + "b";
                }
                if (semitoneDifference == 2) {
                    resultingNote = secondNote + "bb";
                }
                if (semitoneDifference == -1) {
                    resultingNote = secondNote + "#";
                }
                if (semitoneDifference == -2) {
                    resultingNote = secondNote + "##";
                }
            }
        }

        return resultingNote;
    }

    public static String intervalIdentification(String[] args) {
        String firstNote = args[0].substring(0, 1);
        String secondNote = args[1].substring(0, 1);
        String interval = null;
        boolean ascending = true;

        if (args.length == 3 && args[2].equals("dsc")) {
            ascending = false;
        }

        String[] semitoneAndNoteArr = {"C", "2", "D", "2", "E", "1", "F", "2", "G", "2", "A", "2", "B", "1", "C",
                "2", "D", "2", "E", "1", "F", "2", "G", "2", "A", "2", "B", "1", "C"};

        int noteDegree = 0;
        int semitoneCount = 0;
        //find note degree and count of semitones
        if (firstNote.equals(secondNote)) {
            noteDegree = 8;
        } else {
            boolean meetingFistNote = false;
            if (ascending) {
                for (int i = 0; i < semitoneAndNoteArr.length; i++) {
                    if (i % 2 != 0) {
                        semitoneCount += Integer.parseInt(semitoneAndNoteArr[i]);
                    }
                    if (i % 2 == 0) {
                        noteDegree++;
                        if (semitoneAndNoteArr[i].equals(firstNote)) {
                            noteDegree = 1;
                            semitoneCount = 0;
                            meetingFistNote = true;
                        }
                        if (semitoneAndNoteArr[i].equals(secondNote) && meetingFistNote) {
                            break;
                        }
                    }
                }
            } else {
                for (int i = semitoneAndNoteArr.length - 1; i >= 0; i--) {
                    if (i % 2 != 0) {
                        semitoneCount += Integer.parseInt(semitoneAndNoteArr[i]);
                    }
                    if (i % 2 == 0) {
                        noteDegree++;
                        if (semitoneAndNoteArr[i].equals(firstNote)) {
                            noteDegree = 1;
                            semitoneCount = 0;
                            meetingFistNote = true;
                        }
                        if (semitoneAndNoteArr[i].equals(secondNote) && meetingFistNote) {
                            break;
                        }
                    }
                }
            }
        }
        //semitones count correction
        if (ascending) {
            if (args[0].length() == 2) {
                if (args[0].charAt(1) == '#') {
                    semitoneCount--;
                } else if (args[0].charAt(1) == 'b') {
                    semitoneCount++;
                }
            } else if (args[0].length() == 3) {
                if (args[0].substring(1).equals("##")) {
                    semitoneCount -= 2;
                } else if (args[0].substring(1).equals("bb")) {
                    semitoneCount += 2;
                }
            }
            if (args[1].length() == 2) {
                if (args[1].charAt(1) == '#') {
                    semitoneCount++;
                } else if (args[1].charAt(1) == 'b') {
                    semitoneCount--;
                }
            } else if (args[1].length() == 3) {
                if (args[1].substring(1).equals("##")) {
                    semitoneCount += 2;
                } else if (args[1].substring(1).equals("bb")) {
                    semitoneCount -= 2;
                }
            }
        } else {
            if (args[0].length() == 2) {
                if (args[0].charAt(1) == '#') {
                    semitoneCount++;
                } else if (args[0].charAt(1) == 'b') {
                    semitoneCount--;
                }
            } else if (args[0].length() == 3) {
                if (args[0].substring(1).equals("##")) {
                    semitoneCount += 2;
                } else if (args[0].substring(1).equals("bb")) {
                    semitoneCount -= 2;
                }
            }
            if (args[1].length() == 2) {
                if (args[1].charAt(1) == '#') {
                    semitoneCount--;
                } else if (args[1].charAt(1) == 'b') {
                    semitoneCount++;
                }
            } else if (args[1].length() == 3) {
                if (args[1].substring(1).equals("##")) {
                    semitoneCount -= 2;
                } else if (args[1].substring(1).equals("bb")) {
                    semitoneCount += 2;
                }
            }
        }
        //interval identification
        if (semitoneCount == 1 && noteDegree == 2) {
            interval = "m2";
        } else if (semitoneCount == 2 && noteDegree == 2) {
            interval = "M2";
        } else if (semitoneCount == 3 && noteDegree == 3) {
            interval = "m3";
        } else if (semitoneCount == 4 && noteDegree == 3) {
            interval = "M3";
        } else if (semitoneCount == 5 && noteDegree == 4) {
            interval = "P4";
        } else if (semitoneCount == 7 && noteDegree == 5) {
            interval = "P5";
        } else if (semitoneCount == 8 && noteDegree == 6) {
            interval = "m6";
        } else if (semitoneCount == 9 && noteDegree == 6) {
            interval = "M6";
        } else if (semitoneCount == 10 && noteDegree == 7) {
            interval = "m7";
        } else if (semitoneCount == 11 && noteDegree == 7) {
            interval = "M7";
        } else if (semitoneCount == 12 && noteDegree == 8) {
            interval = "P8";
        } else {
            try {
                throw new Exception("Cannot identify the interval");
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
        }

        return interval;
    }
}
