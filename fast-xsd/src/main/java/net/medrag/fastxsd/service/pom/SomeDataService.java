package net.medrag.fastxsd.service.pom;

import net.medrag.fastxsd.pom.SomeData;
import net.medrag.fastxsd.pom.Threshold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * {@author} Stanislav Tretyakov
 * 25.11.2019
 */
@Service
public class SomeDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger("DistinctLogger");
    private static final SomeData[] dataRepo = new SomeData[167];

    static {
        for (int i = 0; i < dataRepo.length; i++) {
            SomeData someData = new SomeData();
            dataRepo[i] = someData;
            someData.setId(i);

            someData.setAlias("Alias" + (int) (Math.random() * 1000));
            someData.setPosition("Pos" + (int) (Math.random() * 1000));
            someData.setNumber((int) (Math.random() * 1000));
            someData.setLocation("location" + (int) (Math.random() * 1000));

            someData.setT1(new Threshold(10, "#0000FF"));
            someData.setT2(new Threshold(20, "#FF0000"));
            someData.setT3(new Threshold(50, "#FFCC00"));
            someData.setT4(new Threshold(70, "#008000"));
        }
    }

    public SomeData[] getAllData() {
        return dataRepo;
    }

    public void saveData(List<SomeData> someData) {
        for (SomeData s : someData) {
            SomeData persisted = dataRepo[s.getId()];
            persisted.setAlias(s.getAlias());
            persisted.setT1(s.getT1());
            persisted.setT2(s.getT2());
            persisted.setT3(s.getT3());
            persisted.setT4(s.getT4());
        }
        LOGGER.info("Data configuration has been saved.");
    }

    public SomeData[] saveIndexes(int[] dbRequest) {
        SomeData[] temp = Arrays.copyOf(dataRepo, dataRepo.length);
        for (int i = 0; i < dbRequest.length; i++) {
            dataRepo[i] = temp[dbRequest[i]];
        }
        LOGGER.info("Data order has been updated.");
        return dataRepo;
    }
}
