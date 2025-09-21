package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class tileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public tileManager (GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        getTileImage();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tiles.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wood.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/table.png"));
            tile[8].collision = true;

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stairs.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int tempCol = 0;
            int tempRow = 0;
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tempRow++;
                    String[] numbers = line.trim().split("\\s+");
                    if (numbers.length > tempCol) {
                        tempCol = numbers.length;
                    }
                }
            }
            br.close();

            gp.maxWorldCol = tempCol;
            gp.maxWorldRow = tempRow;
            mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

            is = getClass().getResourceAsStream(filePath);
            br = new BufferedReader(new InputStreamReader(is));
            int row = 0;

            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String numbers[] = line.trim().split("\\s+");
                for (int col = 0; col < numbers.length && col < gp.maxWorldCol; col++) {
                    if (!numbers[col].isEmpty()) {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                    }
                }
                row++;
            }
            br.close();

        } catch (Exception e) {
            System.out.println("ERRO CRÍTICO: Falha ao carregar o mapa! Verifique o arquivo .txt!");
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            if (mapTileNum == null || worldCol >= mapTileNum.length || worldRow >= mapTileNum[worldCol].length) {
                break;
            }
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}