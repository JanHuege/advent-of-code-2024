import java.io.IOException;
import java.net.URISyntaxException;

import de.huege.helpers.Reader;

void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
    var zz = Reader.readAllLines("day0.txt");

    System.err.println(zz.get(0));
}
