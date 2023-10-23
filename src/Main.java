import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
        public static void main(String[] args) {
            // Создаем главное окно
            JFrame frame = new JFrame("Calculator");

            // Устанавливаем размер окна
            frame.setSize(300, 200);

            // Устанавливаем операцию закрытия окна
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Создаем панель для размещения компонентов
            JPanel panel = new JPanel();

            // Создаем кнопку
            JButton button = new JButton("Запустить класс");

            // Добавляем слушателя событий на кнопку
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Здесь вы можете вызвать ваш класс или метод
                    Calculator calculator = new Calculator();

                    // Например, если ваш класс называется MyClass, то
                    // MyClass.main(new String[0]);
                    JOptionPane.showMessageDialog(frame, "Класс запущен!");
                }
            });

            // Добавляем кнопку на панель
            panel.add(button);

            // Добавляем панель на окно
            frame.add(panel);

            // Делаем окно видимым
            frame.setVisible(true);
        }
    }




