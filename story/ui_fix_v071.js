// v0.7.1 — clarify endings of linear scenes without changing narrative choices.
// The original renderer used the heading "Твой выбор" at the end of *every*
// scene, including scenes with zero choices. This looked like missing buttons.
const renderBeforeUX071 = render;
render = function renderWithUX071(){
  renderBeforeUX071();
  const scene = SCENES[S.scene];
  if (!scene) return;
  const endOfScene = S.line >= sceneLines().length;
  const offersChoice = Array.isArray(scene.choices) && scene.choices.length > 0;
  const isFinale = S.scene === 'II-17';
  const isChapterOneEnd = S.scene === '23';

  if (endOfScene && !offersChoice && !isFinale && !isChapterOneEnd){
    const following = scene.next && SCENES[scene.next];
    $('speaker').textContent = 'Продолжение истории';
    $('lineText').style.display = 'block';
    $('lineText').textContent = following
      ? 'Далее — «' + following.title + '». Здесь нет сюжетной развилки: переходи к следующей сцене.'
      : 'Эпизод завершён. Продолжай историю.';
    $('nextBtn').textContent = 'Следующая сцена ›';
  }
  if (endOfScene && isChapterOneEnd) {
    $('speaker').textContent = 'Глава I завершена';
  }
};
// Chapter II was added in 0.7 but the cover still displayed the 0.6 label.
const edition071 = document.querySelector('#cover .version');
if (edition071) edition071.textContent = 'Иллюстрированное издание · 0.7.1 · Без интернета';
const coverChapter071 = document.querySelector('#cover .under');
if (coverChapter071) coverChapter071.textContent = 'Две главы · Песнь прилива · За гранью волн';
