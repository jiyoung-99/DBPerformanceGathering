package com.skylar.server.handler;

import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.message.ValueObject;
import com.skylar.util.vo.message.MessageVO;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 여러 클라이언트가 보낸 요청을 받아서 처리하는 핸들러
 */
public class InputStreamServerHandler implements Runnable {

    public MyLogger myLogger = LoggerFactory.getLogger(InputStreamServerHandler.class.getSimpleName());

    ObjectInputStream ois;

    public InputStreamServerHandler(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {

        Map<String, StatHandler<? extends ValueObject>> handlerMap = new HashMap<>();
        //해당 경로에 있는 클래스들을 로드해서 classes 안에 넣어준다.
        Set<Class> classes = findAllClassesUsingClassLoader("com.exem.server.handler.statHandler");
        Iterator<Class> iter = classes.iterator();
        while (iter.hasNext()) {
            Class<?> iterClass = iter.next();
            try {
                handlerMap.put(iterClass.getSimpleName(), (StatHandler<? extends ValueObject>) iterClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        try {
            while (true) {
                MessageVO messageVO = (MessageVO) ois.readObject();
                String type = messageVO.getType();
                List<ValueObject> valueObjects = messageVO.getData();
                StatHandler statHandler = handlerMap.get(type);
                //TEST용 코드, error 일 시 무조건 나타나게 한다.
//                myLogger.error("::error::"+ statHandler);
//                myLogger.debug("::debug::"+ statHandler);
                if(statHandler != null) {
                    statHandler.apply(valueObjects);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 패키지 내에 있는 클래스 갖고와서 set에 넣어준다.
     * @param packageName
     * @return
     */
    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            myLogger.error(e.getMessage());
        }
        return null;
    }

}
