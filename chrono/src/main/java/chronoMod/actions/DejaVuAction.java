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
        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(true);

        AbstractDungeon.player.limbo.group.add(card);
        if (card.costForTurn >= 0) {
            card.setCostForTurn(0);
        }
        card.current_x = (Settings.WIDTH / 2.0F);
        card.current_y = (Settings.HEIGHT / 2.0F);
        card.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
        card.target_y = (Settings.HEIGHT / 2.0F);
        card.freeToPlayOnce = true;
        card.purgeOnUse = true;
        card.targetAngle = 0.0F;
        card.drawScale = 0.12F;
        card.applyPowers();

        // this.addToBot(new NewQueueCardAction(card, true, false, true));
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, target, card.energyOnUse, true,
                true), true);

        this.isDone = true;
    }
}
