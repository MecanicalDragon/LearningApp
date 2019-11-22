package oldLessons.human.head;

import oldLessons.human.context.AutoCreate;

@AutoCreate(true)
public class Head {
    Eye leftEye;
    Eye rightEye;
    Nose nose;
    Ear leftEar;
    Ear rightEar;
    Mouth mouth;
    boolean bold;

    public Head() {
    }

    public void setLeftEye(Eye leftEye) {
        this.leftEye = leftEye;
    }

    public void setRightEye(Eye rightEye) {
        this.rightEye = rightEye;
    }

    public void setNose(Nose nose) {
        this.nose = nose;
    }

    public void setLeftEar(Ear leftEar) {
        this.leftEar = leftEar;
    }

    public void setRightEar(Ear rightEar) {
        this.rightEar = rightEar;
    }

    public void setMouth(Mouth mouth) {
        this.mouth = mouth;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    @Override
    public String toString() {
        return "Head{\n" +
                "leftEye=" + leftEye +
                ",\nrightEye=" + rightEye +
                ",\nnose=" + nose +
                ",\nleftEar=" + leftEar +
                ",\nrightEar=" + rightEar +
                ",\nmouth=" + mouth +
                ",\nbold=" + bold +
                '}';
    }
}