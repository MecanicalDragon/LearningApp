package net.medrag.patterns.structure;

/**
 * {@author} Stanislav Tretyakov
 * 29.01.2020
 * <p>
 * > Provides simple interface to complex system;
 * > Allows to divide business logic on different layers
 * <p>
 * + Isolates client from complex system
 * - High risk to become a God Object
 * <p>
 * IMPLEMENTATION
 * <p>
 * Определите, можно ли создать более простой интерфейс, чем тот, который предоставляет сложная подсистема. Вы на
 * правильном пути, если этот интерфейс избавит клиента от необходимости знать о подробностях подсистемы.
 * <p>
 * Создайте класс фасада, реализующий этот интерфейс. Он должен переадресовывать вызовы клиента нужным объектам
 * подсистемы. Фасад должен будет позаботиться о том, чтобы правильно инициализировать объекты подсистемы.
 * <p>
 * Вы получите максимум пользы, если клиент будет работать только с фасадом. В этом случае изменения в подсистеме
 * будут затрагивать только код фасада, а клиентский код останется рабочим.
 * <p>
 * Если ответственность фасада начинает размываться, подумайте о введении дополнительных фасадов.
 */
public class Facade {
    public static void main(String[] args) {
        Post fancyCats = new Image();
        TheWall wall = new TheWall();
        wall.post(fancyCats);
    }
}

interface Post {
}

class Video implements Post {
}

class Image implements Post {
}

class Text implements Post {
}

class VideoDecoder {
    void compressVideo(Video video) {
    }
}

class TextGrammarChecker {
    void checkGrammar(Text text) {
    }
}

class ImageDecorator {
    void decorateImage(Image image) {
    }

    ;
}

class TheWall {
    private VideoDecoder vd = new VideoDecoder();
    private TextGrammarChecker tg = new TextGrammarChecker();
    private ImageDecorator id = new ImageDecorator();

    void post(Post post) {
        //complex logic, that uses a lot of other services
        if (post instanceof Video) {
            vd.compressVideo((Video) post);
        } else if (post instanceof Image) {
            id.decorateImage((Image) post);
        } else if (post instanceof Text) {
            tg.checkGrammar((Text) post);
        }
    }
}