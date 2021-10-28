package chronoMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DejaVuAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard card;

    public DejaVuAction(AbstractCard card) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.p = AbstractDungeon.player;
        if (card != null) {
            this.card = card;
        } else {
            this.isDone = true;
        }
    }

    public void update() {
        AbstractDungeon.player.limbo.group.add(this.card);
        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(true);

        if (target != null) {
            this.card.calculateCardDamage(target);
        }
        // this.card.freeToPlayOnce = true;
        this.card.purgeOnUse = true;
        // this.card.applyPowers();

        this.card.current_x = (Settings.WIDTH / 2.0F);
        this.card.current_y = (Settings.HEIGHT / 2.0F);
        this.card.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
        this.card.target_y = (Settings.HEIGHT / 2.0F);
        this.card.targetAngle = 0.0F;
        this.card.drawScale = 0.12F;

        // this.addToBot(new NewQueueCardAction(card, true, false, true));
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(this.card, target, this.card.energyOnUse,
                true, true), true);

        this.isDone = true;
    }
}
