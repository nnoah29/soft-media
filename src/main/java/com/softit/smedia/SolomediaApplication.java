package com.softit.smedia;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.lang.management.ManagementFactory;


@SpringBootApplication
@EnableScheduling
@EnableWebSocket
public class SolomediaApplication implements CommandLineRunner {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SolomediaApplication.class);
    private static ApplicationContext applicationContext = null;

    public static void main(String[] args) {
//        SpringApplication.run(SolomediaApplication.class, args);
        String mode = args != null && args.length > 0 ? args[0] : null;

        if (log.isDebugEnabled()) {
            log.debug("PID:" + ManagementFactory.getRuntimeMXBean().getName() + " Application mode:" + mode
                    + " context:" + applicationContext);
        }
        if (applicationContext != null && mode != null && "stop".equals(mode)) {
            System.exit(SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
                @Override
                public int getExitCode() {
                    return 0;
                }
            }));
        } else {
            SpringApplication app = new SpringApplication(SolomediaApplication.class);
            applicationContext = app.run(args);
            if (log.isDebugEnabled()) {
                log.debug("PID:" + ManagementFactory.getRuntimeMXBean().getName() + " Application started context:"
                        + applicationContext);
            }
        }
    }

    @Override
    public void run(String... strings) throws Exception {
		/*Map<String,String> media = new HashMap<>();
		List<String> listm = new ArrayList<>();
		File file = new File(VIDEO_ROOT);
		File[] listFile = file.listFiles();

		for (File lisf1 : listFile) {
			System.out.println("liste fille " + lisf1.getName());
			listm.add(lisf1.getName());
		}
		media.put("video", listm.toString());
		Path path2 =Paths.get("./", "media1");
		try {
			if(path2.toFile().exists()){
				System.out.println("existe");
			}else
			{
				Path path3 = Files.createDirectories(path2);
			}

		} catch (IOException ex) {
			//Logger.getLogger(SolomediaApplication.class.getName()).log(Level.SEVERE, null, ex);
		}


		Writer wr = new FileWriter(new File("./media1/video.json"));
		wr.write(media.toString());
		wr.flush();
		List<String> medialist = new ArrayList<>();
		medialist.add("01.mp4");
//		medialist.add("spot.mp4");
//		medialist.add("film.avi");
		medialist.add("video5.mp4");
		medialist.forEach(s -> {
			System.out.println("starring ... "+s);
			try {
				new DownloadMediaControleur().downloadFile(s);
//				new DownloadMediaControleur().fetchFile(s);
			} catch (IOException e) {

			}

		});
		new DownloadMediaControleur().backupAndMoveToMedia();
		new PlayListController().getPlayListFromServer(37);
		//genJsonFile(getPlayList());
		//fetchFile("video4.mp4");*/

    }


}
