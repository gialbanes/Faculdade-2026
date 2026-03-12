
-- 1. Criação da tabela de Cursos
CREATE TABLE IF NOT EXISTS public.courses (
                                              id SERIAL PRIMARY KEY,
                                              title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    "imageUrl" TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
    );

-- 2. Criação da tabela de Lições
CREATE TABLE IF NOT EXISTS public.lessons (
                                              id SERIAL PRIMARY KEY,
                                              course_id INTEGER NOT NULL REFERENCES public.courses(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    "orderIndex" INTEGER NOT NULL,
    xp_reward INTEGER DEFAULT 10,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
    );

-- 3. Criação da tabela de Questões
CREATE TABLE IF NOT EXISTS public.questions (
                                                id SERIAL PRIMARY KEY,
                                                lesson_id INTEGER NOT NULL REFERENCES public.lessons(id) ON DELETE CASCADE,
    "questionText" TEXT NOT NULL,
    options JSONB NOT NULL,
    "correctOptionIndex" INTEGER NOT NULL,
    explanation TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT timezone('utc'::text, now()) NOT NULL
    );

-- Habilitar Row Level Security (RLS) - por enquanto liberado para testes (anon)
ALTER TABLE public.courses ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.lessons ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.questions ENABLE ROW LEVEL SECURITY;

CREATE POLICY "Allow anon read/write access courses" ON public.courses FOR ALL USING (true) WITH CHECK (true);
CREATE POLICY "Allow anon read/write access lessons" ON public.lessons FOR ALL USING (true) WITH CHECK (true);
CREATE POLICY "Allow anon read/write access questions" ON public.questions FOR ALL USING (true) WITH CHECK (true);

-- 4. Inserindo dados iniciais (Seed)
INSERT INTO public.courses (title, description, "imageUrl") VALUES
                                                                ('Kotlin Multiplatform', 'Aprenda a criar apps para Android e iOS com uma única base de código', 'https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin_Icon.png'),
                                                                ('Introdução ao React Native', 'Construa aplicações mobile multiplataforma usando JavaScript e React', 'https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg')
    ON CONFLICT DO NOTHING;

INSERT INTO public.lessons (course_id, title, content, "orderIndex", xp_reward) VALUES
                                                                                    (1, 'O que é Kotlin?', 'Kotlin é uma linguagem moderna, estaticamente tipada, que roda na JVM.', 1, 15),
                                                                                    (1, 'Configurando o Ambiente KMP', 'Aprenda a instalar e configurar o Android Studio e o Kotlin Multiplatform Mobile plugin.', 2, 20),
                                                                                    (2, 'O que é React Native?', 'React Native é um framework para criar aplicativos nativos usando React.', 1, 15)
    ON CONFLICT DO NOTHING;

INSERT INTO public.questions (lesson_id, "questionText", options, "correctOptionIndex", explanation) VALUES
                                                                                                         (1, 'Em qual máquina virtual o Kotlin roda primariamente?', '["Dalvik", "JVM", "V8", "CLR"]', 1, 'Kotlin foi originalmente projetado para rodar na Java Virtual Machine (JVM).'),
                                                                                                         (1, 'Kotlin é uma linguagem de tipagem estática ou dinâmica?', '["Estática", "Dinâmica", "Ambas", "Nenhuma"]', 0, 'Kotlin é estaticamente tipado, o que significa que o tipo das variáveis é conhecido em tempo de compilação.'),
                                                                                                         (3, 'Qual linguagem base é usada no React Native?', '["Java", "Kotlin", "Swift", "JavaScript"]', 3, 'React Native utiliza o JavaScript junto com a biblioteca React.')
    ON CONFLICT DO NOTHING;