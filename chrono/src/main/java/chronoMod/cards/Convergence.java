package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.ConvergencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class Convergence extends AbstractDynamicCard {
    private static final String CARD_ID = Convergence.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("attack/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int DAMAGE_BLOCK_AMOUNT = 6;
    private static final int UPGRADE_PLUS_AMOUNT = 2;

    public Convergence() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE_BLOCK_AMOUNT;
        this.baseBlock = DAMAGE_BLOCK_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new ApplyPowerAction(p, p, new ConvergencePower(p, this.block), this.block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_AMOUNT);
            upgradeBlock(UPGRADE_PLUS_AMOUNT);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Convergence();
    }
}
